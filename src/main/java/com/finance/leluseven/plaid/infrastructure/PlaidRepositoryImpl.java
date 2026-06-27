package com.finance.leluseven.plaid.infrastructure;

import com.finance.leluseven.plaid.domain.ContaBancaria;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.transacao.domain.Transacao;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaidRepositoryImpl implements IPlaidRepository {

    private final PlaidApi plaidApi;
    private final PlaidConfig plaidConfig;

    @Override
    public String criarLinkToken(String userId) {
        try {
            var user = new LinkTokenCreateRequestUser().clientUserId(userId);

            var request = new LinkTokenCreateRequest()
                    .user(user)
                    .clientName("Meu App")
                    .products(List.of(Products.TRANSACTIONS))
                    .countryCodes(List.of(CountryCode.PT))
                    .language("pt");

            var response = plaidApi.linkTokenCreate(request).execute();

            if (response.body() == null || response.body().getLinkToken().isEmpty()) {
                throw new AssertionError("Token vazio");
            }

            return response.body().getLinkToken();

        } catch (IOException e) {
            throw new DomainException("Erro ao criar link token: " + e.getMessage());
        }
    }

    @Override
    public PlaidToken trocarPublicToken(String publicToken) {
        try {
            var request = new ItemPublicTokenExchangeRequest()
                    .publicToken(publicToken);

            var response = plaidApi.itemPublicTokenExchange(request).execute();

            if (response.body() == null)
                throw new AssertionError("Token vazio!");

            return PlaidToken.de(response.body().getAccessToken(), response.body().getItemId());

        } catch (IOException e) {
            throw new DomainException("Erro ao trocar token: " + e.getMessage());
        }
    }

    @Override
    public List<ContaBancaria> listarContas(PlaidToken plaidToken) {
        try {
            var request = new AccountsGetRequest()
                    .accessToken(plaidToken.accessToken());

            var response = plaidApi.accountsGet(request).execute();

            if (response.body() == null)
                throw new AssertionError("Não foi possível encontrar contas!");

            return response.body().getAccounts().stream()
                    .map(this::toContaBancaria)
                    .toList();

        } catch (IOException e) {
            throw new DomainException("Erro ao listar contas: " + e.getMessage());
        }
    }

    @Override
    public List<Transacao> listarTransacoes(PlaidToken plaidToken,
                                            LocalDate inicio,
                                            LocalDate fim) {
        try {
            var request = new TransactionsGetRequest()
                    .accessToken(plaidToken.accessToken())
                    .startDate(inicio)
                    .endDate(fim);

            var response = plaidApi.transactionsGet(request).execute();

            if (response.body() == null)
                throw new AssertionError("Ocorreu um erro inesperado durante a transação!");

            return response.body().getTransactions().stream()
                    .map(this::toTransacao)
                    .toList();

        } catch (IOException e) {
            throw new DomainException("Erro ao listar transações: " + e.getMessage());
        }
    }

    // conversores
    private ContaBancaria toContaBancaria(AccountBase raw) {
        return ContaBancaria.criar(raw);
    }

    private Transacao toTransacao(Transaction raw) {
        return Transacao.dePlaid(
                raw.getTransactionId(),
                raw.getName(),
                new BigDecimal(raw.getAmount()),
                raw.getDate(),
                raw.getCategory().stream().findFirst().get()
        );
    }
}
