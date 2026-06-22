package com.finance.lululeleseven.perfil.application.dto;

import com.finance.lululeleseven.shared.exception.DomainException;

public record PerfilDto(String nome) {
    public PerfilDto {
        if (nome == null || nome.isBlank()) {
            throw new DomainException("Perfil deve ser informado");
        }
    }
}
