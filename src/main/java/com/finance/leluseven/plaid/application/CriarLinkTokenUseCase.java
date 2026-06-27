package com.finance.leluseven.plaid.application;

import com.finance.leluseven.plaid.domain.IPlaidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarLinkTokenUseCase {
    private final IPlaidRepository plaidRepository;

    public String execute(String userId) {
        return plaidRepository.criarLinkToken(userId);
    }
}
