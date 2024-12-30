package com.example.shop_service.exception;

public class ProductAlreadyExist extends RuntimeException{

    public ProductAlreadyExist(String article) {
        super("product with article: " + article + " already exist");
    }

}
