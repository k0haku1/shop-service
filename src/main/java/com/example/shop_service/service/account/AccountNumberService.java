package com.example.shop_service.service.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountNumberService {

    CompletableFuture<Map<String, String>> getAccountNumber (List<String> logins);

}
