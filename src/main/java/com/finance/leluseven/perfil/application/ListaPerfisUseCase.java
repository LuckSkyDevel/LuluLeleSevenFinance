package com.finance.leluseven.perfil.application;

import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaPerfisUseCase {
    private final IPerfilRepository repo;

    public List<Perfil> execute() {
        var perfis = repo.listaTodosPerfis();

        if(perfis.isEmpty()) {
            throw new DataNotFoundException("Nenhum perfil encontrado");
        }

        return perfis;
    }
}
