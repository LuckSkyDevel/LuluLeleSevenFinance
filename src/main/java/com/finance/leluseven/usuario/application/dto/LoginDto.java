package com.finance.leluseven.usuario.application.dto;

import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;

public record LoginDto(NomeUsuario nomeUsuario, String senha) {
    public LoginDto {
        if (senha == null || senha.isBlank()) {
            throw new DomainException("Senha não informada!");
        }
    }
}
