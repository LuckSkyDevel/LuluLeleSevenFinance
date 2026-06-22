package com.finance.lululeleseven.refreshtoken.infrastructure;

import com.finance.lululeleseven.perfil.infrastructure.PerfilEntity;
import com.finance.lululeleseven.refreshtoken.domain.RefreshToken;
import com.finance.lululeleseven.usuario.domain.Usuario;
import com.finance.lululeleseven.usuario.infrastructure.UsuarioEntity;

public class RefreshTokenMapper {

    // JPA entity → domain
    public RefreshToken toDomain(RefreshTokenEntity entity) {
        var usuarioBanco = entity.getUsuario();

        var perfis = usuarioBanco.getPerfis().stream().map(PerfilEntity::getNomPerfil).toList();

        var usuario = Usuario.reconstituir(
                usuarioBanco.getCodUsuario(),
                usuarioBanco.getDesEmail(),
                usuarioBanco.getDesEmail(),
                usuarioBanco.getSenhaHash(),
                perfis,
                usuarioBanco.getDatCriacao()
        );

        return RefreshToken.recriar(
                entity.getCodRefreshToken(),
                entity.getRefreshToken(),
                entity.getDatExpiracao(),
                entity.getDispositivo(),
                entity.getRevogado(),
                usuario
        );
    }

    // domain → JPA entity
    public RefreshTokenEntity toEntity(RefreshToken domain) {
        var entity = new RefreshTokenEntity();
        entity.setRefreshToken(domain.getToken());
        entity.setDatExpiracao(domain.getDatExpiracao());
        entity.setDispositivo(domain.getDispositivo().valor());

        var usuario = new UsuarioEntity();
        usuario.setCodUsuario(domain.getUsuario().getCodUsuario().valor());
        entity.setUsuario(usuario);

        return entity;
    }
}
