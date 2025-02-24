package com.example.shop_service.persistence.repository;

import com.example.shop_service.persistence.models.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {

    boolean existsByArticle(String article);

    @Query("SELECT p.name FROM Product p WHERE p.id = :productId")
    String findProductNameById(@Param("productId") UUID productId);

}
