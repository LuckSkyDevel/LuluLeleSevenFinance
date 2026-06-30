package com.finance.leluseven.transacao.domain;

import com.finance.leluseven.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITransacaoRepository {
    Transacao save(Transacao transacao);

    List<Transacao> findByUsuarioId(CodUsuario codUsuario);

    List<Transacao> findByUsuarioIdAndPeriodo(CodUsuario codUsuario, LocalDate inicio, LocalDate fim);

    boolean existsByPlaidId(String plaidTransacaoId);

    Optional<Transacao> findByPlaidTransacaoId(String plaidTransacaoId);

    Optional<Transacao> findById(CodTransacao codTransacao);

    void deleteByPlaidId(String plaidTransacaoId);

}
