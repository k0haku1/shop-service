package com.example.shop_service.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfo {

    private Long id;
    private String accountNumber;
    private String email;
    private String inn;

}
