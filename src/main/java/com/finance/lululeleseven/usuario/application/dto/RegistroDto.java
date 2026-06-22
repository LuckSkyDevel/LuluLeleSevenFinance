package com.finance.lululeleseven.usuario.application.dto;

import com.finance.lululeleseven.shared.exception.DomainException;
import com.finance.lululeleseven.usuario.domain.vo.Email;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;

public record RegistroDto(NomeUsuario nome, Email email, String senha) {
    public RegistroDto {
        if (senha == null || senha.length() < 8) {
            throw new DomainException("Senha deve ter pelo menos 8 caracteres");
        }

        if (senha.matches("^[0-9]+$")) {
            throw new DomainException("A Senha deve conter pelo menos 1 letra");
        }

        if (senha.equals(senha.toLowerCase())) {
            throw new DomainException("A Senha deve conter pelo menos 1 letra maiuscula");
        }

        if (senha.equals(senha.toUpperCase())) {
            throw new DomainException("A Senha deve conter pelo menos 1 letra maiuscula");
        }

        if (senha.matches("^[a-zA-Z0-9]+$")) {
            throw new DomainException("A Senha deve conter pelo menos 1 caractere especial");
        }
    }
}
