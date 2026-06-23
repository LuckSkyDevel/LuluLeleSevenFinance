package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CriaRefreshTokenUseCase {

    private final IUsuarioRepository repoUsuario;
    private final IRefreshTokenRepositroy repoRefreshToken;
    private final TokenService tokenService;

    public RefreshToken execute(String rToken) {

        var nomeUsuario = tokenService.extractUsername(rToken);
        var usuario = repoUsuario.findByNomUsuario(NomeUsuario.de(nomeUsuario));

        if (usuario.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Usuario no encontrado");
        }

        var rTokenObject = new RefreshToken();

        try {
            InetAddress ipLocal = InetAddress.getLocalHost();
            var dispositivo = ipLocal.getHostAddress();

            Date now = new Date();
            var dataExpiracao = new Date(now.getTime() + tokenService.getRefreshExpiration() * 1000).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

            rTokenObject = RefreshToken.criar(rToken, dataExpiracao, dispositivo, false, usuario.get(), LocalDateTime.now());
            return repoRefreshToken.salvar(rTokenObject);

        } catch (UnknownHostException e) {
            throw new RuntimeException("Não foi possível recuperar o Ip do dispositivo!" + e.getMessage());
        }
    }
}
