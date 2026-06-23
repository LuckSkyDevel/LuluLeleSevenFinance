package com.finance.leluseven.refreshtoken.domain;

import java.util.Optional;

public interface IRefreshTokenRepositroy {
    Optional<RefreshToken> recuperaRefreshTokenPorToken(String token);

    Optional<RefreshToken> recuperaRefreshTokenPorCodigo(Long codRefreshToken);

    Optional<RefreshToken> recuperaRefreshTokenPorCodUsuario(Long codUsuario);

    RefreshToken salvar(RefreshToken refreshToken);

}
