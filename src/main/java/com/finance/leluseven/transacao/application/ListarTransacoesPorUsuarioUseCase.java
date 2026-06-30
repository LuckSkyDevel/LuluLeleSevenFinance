package com.finance.leluseven.transacao.application;

import com.finance.leluseven.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTransacoesPorUsuarioUseCase {

    private final ITransacaoRepository repo;

    public List<Transacao> execute(CodUsuario codUsuario) {
        return repo.findByUsuarioId(codUsuario);
    }

    public List<Transacao> executeForPeriod(CodUsuario codUsuario, LocalDate inicio, LocalDate fim) {
        return repo.findByUsuarioIdAndPeriodo(codUsuario, inicio, fim);
    }
}
