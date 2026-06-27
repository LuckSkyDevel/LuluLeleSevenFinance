package com.finance.leluseven.plaid.application;

import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocarPublicTokenUseCase {
    private final IPlaidRepository plaidRepository;
    private final IUsuarioRepository usuarioRepository;

    public void execute(String publicToken, CodUsuario usuarioId) {
        // troca o public token pelo access token
        var resposta = plaidRepository.trocarPublicToken(publicToken);

        // salva o access token vinculado ao usuário
        var usuario = usuarioRepository.findByCodUsuario(usuarioId)
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

        usuario.vincularPlaid(resposta.accessToken(), resposta.itemId());
        usuarioRepository.save(usuario);
    }
}
