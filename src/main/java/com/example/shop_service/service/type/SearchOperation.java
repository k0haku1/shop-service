package com.example.shop_service.service.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.List;

@Getter
public enum SearchOperation {

    EQUAL("=", "EQUAL", "eq"),
    GRATER_THAN_OR_EQ(">=", "GRATER_THAN_OR_EQ", "gte"),
    LESS_THAN_OR_EQ("<=", "LESS_THAN_OR_EQ", "lte"),
    LIKE("~", "LIKE", "contains");


    private final List<String> symbols;

    SearchOperation(String... symbols) {
        this.symbols = List.of(symbols);
    }

    @JsonCreator
    public static SearchOperation fromSymbol(String symbol) {
        for (SearchOperation operation : SearchOperation.values()) {
            if (operation.getSymbols().contains(symbol)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Unknown symbol: " + symbol);
    }

}
