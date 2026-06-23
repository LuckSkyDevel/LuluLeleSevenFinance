package com.finance.leluseven.perfil.application;

import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecuperaPerfilUseCase {
    private final IPerfilRepository repo;

    public Perfil execute(Long codPerfil) {
        var perfil = repo.recuperaPerfilPorCodigo(codPerfil);

        if (perfil.isEmpty()) {
            throw new DataNotFoundException("Não foi possível recuperar o perfil!");
        }

        return perfil.get();
    }

    public Perfil execute(String nomPerfil) {
        if (nomPerfil.isEmpty()) {
            throw new DataNotFoundException("O campo nome perfil é obrigatório!");
        }

        var perfil = repo.recuperaPerfilPorNome(nomPerfil);

        if (perfil.isEmpty()) {
            throw new DataNotFoundException("Não foi possível recuperar o perfil!");
        }

        return perfil.get();
    }
}
