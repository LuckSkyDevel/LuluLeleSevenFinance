package com.finance.leluseven.plaid.application;

import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTransacoesUseCase {
    private final IPlaidRepository repo;
    private final IUsuarioRepository repoUsuario;

    public List<Transacao> execute(CodUsuario codUsuario, @RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        var usuario = repoUsuario.findByCodUsuario(codUsuario).orElseThrow(() -> new DataNotFoundException("Transações não podem ser listadas para o usuario, pois não encontrado!"));

        var plaidToken = PlaidToken.de(usuario.getPlaidAccessToken(), usuario.getPlaidItemId());

        return repo.listarTransacoes(plaidToken, inicio, fim);
    }
}
