package com.example.shop_service.persistence.models;


import com.example.shop_service.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
public class OrderEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
    
}
