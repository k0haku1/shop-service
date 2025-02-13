package com.example.shop_service.service;


import com.example.shop_service.service.account.AccountNumberService;
import com.example.shop_service.service.crm.AccountInnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountDataProvider {

    private final AccountNumberService accountNumberService;
    private final AccountInnService accountInnService;

    public CompletableFuture <Map<String, String>> getAccountNumber(List<String> logins) {
        return accountNumberService.getAccountNumber(logins);
    }

    public CompletableFuture <Map<String, String>> getAccountInn(List<String> logins) {
        return accountInnService.getAccountInn(logins);
    }

}
