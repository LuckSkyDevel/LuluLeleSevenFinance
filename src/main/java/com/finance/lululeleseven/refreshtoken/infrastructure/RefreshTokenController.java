package com.finance.lululeleseven.refreshtoken.infrastructure;

import com.finance.lululeleseven.refreshtoken.application.AtualizaRefreshTokenUseCase;
import com.finance.lululeleseven.refreshtoken.application.RecuperaRefreshTokenPorCodUsuarioUseCase;
import com.finance.lululeleseven.refreshtoken.domain.RefreshToken;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resfres-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final AtualizaRefreshTokenUseCase atualizaRefreshTokenUseCase;
    private final RecuperaRefreshTokenPorCodUsuarioUseCase recuperaRefreshTokenPorCodUsuarioUseCase;

    @PutMapping("/usuario/{codUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<RefreshToken> atualizaRefreshTokenPorCodUsuario(@PathVariable Long codUsuario, @RequestBody @NotNull String refreshToken) {
        return ResponseEntity.ok(atualizaRefreshTokenUseCase.execute(codUsuario, refreshToken));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<RefreshToken> recuperaRefreshTokenPorCodUsuario(Long codUsuario) {
        return ResponseEntity.ok(recuperaRefreshTokenPorCodUsuarioUseCase.execute(codUsuario));
    }
}
