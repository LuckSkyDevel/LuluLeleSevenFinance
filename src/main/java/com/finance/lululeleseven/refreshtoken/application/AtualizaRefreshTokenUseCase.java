package com.finance.lululeleseven.refreshtoken.application;

import com.finance.lululeleseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.lululeleseven.refreshtoken.domain.RefreshToken;
import com.finance.lululeleseven.shared.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AtualizaRefreshTokenUseCase {

    private final IRefreshTokenRepositroy repo;
    private final TokenService tokenService;

    public void execute(Long codUsuario, String refreshToken) {
        var refresh = repo.recuperaRefreshTokenPorCodUsuario(codUsuario);

        if (refresh.isPresent()) {
            Date now = new Date();
            var dataExpiracao = new Date(now.getTime() + tokenService.getRefreshExpiration() * 1000).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

            RefreshToken.recriar(refresh.get().getCodRefreshToken().valor(), refreshToken, dataExpiracao, refresh.get().getDispositivo().valor(),
                    refresh.get().getIsRevogado(), refresh.get().getUsuario());

            repo.salvar(refresh.get());
        }
    }
}
