package com.finance.leluseven.transacao.application;

import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SincronizarTransacoesUseCase {

    private final IPlaidRepository plaidRepository;
    private final ITransacaoRepository transacaoRepository;
    private final IUsuarioRepository usuarioRepository;

    @Transactional
    public void execute(CodUsuario codUsuario) {
        var usuario = usuarioRepository.findByCodUsuario(codUsuario)
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

        if (!usuario.temPlaidVinculado())
            throw new DomainException("Usuário não vinculou conta bancária");

        var cursor = usuario.getPlaidCursor();  // null na primeira vez
        boolean temMaisPaginas = true;

        while (temMaisPaginas) {
            var resultado = plaidRepository.sincronizarTransacoes(
                    PlaidToken.de(usuario.getPlaidAccessToken(), ""), cursor
            );

            // processa adicionadas e modificadas
            processarTransacoes(resultado.adicionadas(), codUsuario);
            processarTransacoes(resultado.modificadas(), codUsuario);

            // processa removidas
            resultado.removidasIds().forEach(transacaoRepository::deleteByPlaidId);

            cursor = resultado.novoCursor();
            temMaisPaginas = resultado.temMaisPaginas();
        }

        // salva o cursor atualizado para a próxima sincronização
        usuario.atualizarPlaidCursor(cursor);
        usuarioRepository.updatePlaidCursor(codUsuario, cursor);
    }

    private void processarTransacoes(List<Transacao> transacoes, CodUsuario codUsuario) {
        transacoes.forEach(t -> {
            var transacao = transacaoRepository.findByPlaidTransacaoId(t.getPlaidTransacaoId());

            if (transacao.isPresent()) {
                // atualiza se já existe (caso de "modified")
                var transacaoModificada = transacao.get();
                transacaoModificada.atualizar(transacaoModificada, t.getDescricao(), t.getValor().valor(), t.getCategoria());
                transacaoRepository.save(transacaoModificada);
            } else {
                // cria nova (caso de "added")
                var nova = Transacao.de(
                        t.getPlaidTransacaoId(), t.getDescricao(), t.getValor().valor(),
                        t.getDataTransacao(), t.getCategoria(), t.getCodUsuario()
                );
                transacaoRepository.save(nova);
            }
        });
    }
}
