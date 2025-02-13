package com.example.shop_service.service.crm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountInnService {

    CompletableFuture <Map<String, String>> getAccountInn (List<String> logins);

}
