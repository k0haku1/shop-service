package com.example.shop_service.persistence.repository;

import com.example.shop_service.persistence.models.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {
    boolean existsByArticle(String article);
}
