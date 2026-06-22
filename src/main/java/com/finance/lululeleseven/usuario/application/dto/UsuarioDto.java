package com.finance.lululeleseven.usuario.application.dto;

import com.finance.lululeleseven.shared.exception.DomainException;
import com.finance.lululeleseven.usuario.domain.Usuario;
import com.finance.lululeleseven.usuario.domain.vo.CodUsuario;
import com.finance.lululeleseven.usuario.domain.vo.Email;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;

import java.util.List;

public record UsuarioDto(CodUsuario codUsuario, NomeUsuario nome, Email email, List<String> perfis, String accessToken,
                         String refreshToken) {
    public UsuarioDto {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new DomainException("Usuário não foi autenticado!");
        }
    }

    public static UsuarioDto de(Usuario usuario, String accessToken, String refreshToken) {
        return new UsuarioDto(
                usuario.getCodUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis(),
                accessToken,
                refreshToken
        );
    }
}
