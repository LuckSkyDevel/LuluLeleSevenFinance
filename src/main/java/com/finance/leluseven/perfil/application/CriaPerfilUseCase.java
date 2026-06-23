package com.finance.leluseven.perfil.application;

import com.finance.leluseven.perfil.application.dto.PerfilDto;
import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.perfil.domain.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriaPerfilUseCase {
    private final IPerfilRepository repo;

    public Perfil executa(PerfilDto perfilDto) {
        var perfil = Perfil.criar(perfilDto.nome(), perfilDto.descricao());
        return repo.salvar(perfil);
    }
}
