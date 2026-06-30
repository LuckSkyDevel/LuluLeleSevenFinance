package com.finance.leluseven.plaid.domain.vo;

import com.finance.leluseven.transacao.domain.Transacao;

import java.util.List;

public record SyncResult(
        List<Transacao> adicionadas,
        List<Transacao> modificadas,
        List<String> removidasIds,
        String novoCursor,
        boolean temMaisPaginas
) {
}
