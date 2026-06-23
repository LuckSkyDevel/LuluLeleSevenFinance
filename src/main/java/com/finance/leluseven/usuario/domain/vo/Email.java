package com.finance.leluseven.usuario.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record Email(String valor) {
    public Email {
        if (valor == null || !valor.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new DomainException("E-mail inválido: " + valor);
    }

    public static Email de(String valor) {
        return new Email(valor);
    }
}
