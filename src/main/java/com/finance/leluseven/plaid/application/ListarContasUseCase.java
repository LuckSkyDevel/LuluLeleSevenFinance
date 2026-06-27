package com.finance.leluseven.plaid.application;

import com.finance.leluseven.plaid.domain.ContaBancaria;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarContasUseCase {
    private final IPlaidRepository repo;
    private final IUsuarioRepository repoUsuario;

    public List<ContaBancaria> execute(CodUsuario codUsuario) {
        var usuario = repoUsuario.findByCodUsuario(codUsuario);

        if (usuario.isEmpty()){
            throw new DataNotFoundException("Não foi possÍvel listar as contas pelo codigo de usuario informado!");
        }

        var plaidToken = PlaidToken.de(usuario.get().getPlaidAccessToken(), usuario.get().getPlaidItemId());

        return repo.listarContas(plaidToken);
    }
}
