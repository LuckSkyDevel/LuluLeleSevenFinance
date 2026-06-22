package com.finance.lululeleseven.usuario.application.dto;

import com.finance.lululeleseven.shared.exception.DomainException;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record LoginDto(NomeUsuario nomeUsuario, String senha) {
    public LoginDto {
        if (senha == null || senha.isBlank()) {
            throw new DomainException("Senha não informada!");
        }
    }
}
