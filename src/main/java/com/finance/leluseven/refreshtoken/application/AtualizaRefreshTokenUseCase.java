package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AtualizaRefreshTokenUseCase {

    private final IRefreshTokenRepositroy repo;
    private final TokenService tokenService;

    public RefreshToken execute(Long codUsuario, String refreshToken) {
        var refresh = repo.recuperaRefreshTokenPorCodUsuario(codUsuario);

        if (!refresh.isPresent()) {
            throw new DataNotFoundException("Refresh token não encontrado para o usuario informado!");
        }

        Date now = new Date();
        var dataExpiracao = new Date(now.getTime() + tokenService.getRefreshExpiration() * 1000).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        RefreshToken.recriar(refresh.get().getCodRefreshToken().valor(), refreshToken, dataExpiracao, refresh.get().getDispositivo().valor(),
                refresh.get().getIsRevogado(), refresh.get().getUsuario());

        return repo.salvar(refresh.get());
    }
}
