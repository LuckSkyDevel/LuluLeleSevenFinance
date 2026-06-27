package com.finance.leluseven.transacao.domain;

import com.finance.leluseven.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.transacao.domain.vo.Valor;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacao {
    private CodTransacao codigoTransacao;
    private CodUsuario codUsuario;
    private String plaidTransacaoId;
    private String descricao;
    private Valor valor;
    private String categoria;
    private LocalDate data;
    private String contaId;

    public static Transacao dePlaid(String plaidId, String descricao, BigDecimal valor, LocalDate data,
                                    String categoria) {
        var transacao = new Transacao();
        transacao.plaidTransacaoId = plaidId;
        transacao.descricao = descricao;
        transacao.valor = Valor.de(valor);
        transacao.data = data;
        transacao.categoria = categoria;
        return transacao;
    }

    public static Transacao de(String plaidId, String descricao, BigDecimal valor, LocalDate data,
                                    String categoria, CodUsuario codUsuario) {
        var transacao = new Transacao();
        transacao.plaidTransacaoId = plaidId;
        transacao.descricao = descricao;
        transacao.valor = Valor.de(valor);
        transacao.data = data;
        transacao.categoria = categoria;
        transacao.codUsuario = codUsuario;
        return transacao;
    }

    public boolean isDebito() {
        return valor.valor().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isCredito() {
        return valor.valor().compareTo(BigDecimal.ZERO) < 0;
    }

}
