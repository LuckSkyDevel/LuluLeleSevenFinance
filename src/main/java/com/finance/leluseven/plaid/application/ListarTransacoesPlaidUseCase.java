package com.finance.leluseven.plaid.application;

import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTransacoesPlaidUseCase {
    private final IPlaidRepository repo;
    private final IUsuarioRepository repoUsuario;

    public List<Transacao> execute(CodUsuario codUsuario, LocalDate inicio, LocalDate fim) {
        var usuario = repoUsuario.findByCodUsuario(codUsuario).orElseThrow(() ->
                new DataNotFoundException("Transações não podem ser listadas para o usuario, pois ele não foi encontrado!"));

        var plaidToken = PlaidToken.de(usuario.getPlaidAccessToken(), usuario.getPlaidItemId());

        return repo.listarTransacoes(plaidToken, inicio, fim);
    }
}
