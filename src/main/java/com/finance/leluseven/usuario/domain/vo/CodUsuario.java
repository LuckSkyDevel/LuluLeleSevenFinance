package com.finance.leluseven.usuario.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodUsuario(Long valor) {

    public CodUsuario {
        if (valor == null || valor <= 0)
            throw new DomainException("ID inválido"); // Criar DomainException
    }

    public static CodUsuario de(Long valor) {
        return new CodUsuario(valor);
    }
}
