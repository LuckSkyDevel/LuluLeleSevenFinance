package com.finance.leluseven.plaid.domain;

import com.finance.leluseven.plaid.domain.vo.CodContaBancaria;
import com.plaid.client.model.AccountBase;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ContaBancaria {
    private CodContaBancaria codContaBancaria;
    private String nome;
    private String nomeOficial;
    private BigDecimal saldo;
    private String tipoConta;

    public static ContaBancaria criar(AccountBase raw) {
        var contaBancaria = new ContaBancaria();
        contaBancaria.codContaBancaria = CodContaBancaria.de(raw.getAccountId());
        contaBancaria.nome = raw.getName();
        contaBancaria.nomeOficial = raw.getOfficialName();
        contaBancaria.saldo = new BigDecimal(raw.getBalances().getCurrent());
        contaBancaria.tipoConta = raw.getType().getValue();
        return contaBancaria;
    }
}
