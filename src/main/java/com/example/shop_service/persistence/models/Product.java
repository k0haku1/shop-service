package com.example.shop_service.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "article")
    private String article;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "is_available", nullable = false)
    private Boolean is_available;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProducts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List <ProductImage> images;

}
