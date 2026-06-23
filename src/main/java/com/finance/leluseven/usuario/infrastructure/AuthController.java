package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.usuario.application.CriarUsuarioUseCase;
import com.finance.leluseven.usuario.application.LoginUseCase;
import com.finance.leluseven.usuario.application.dto.LoginDto;
import com.finance.leluseven.usuario.application.dto.RegistroDto;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import com.finance.leluseven.usuario.domain.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/registra-usuario")
    public ResponseEntity<Usuario> registraUsuario(@Valid @RequestBody RegistroDto registroDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(criarUsuarioUseCase.execute(registroDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(loginUseCase.execute(loginDto));
    }
}
