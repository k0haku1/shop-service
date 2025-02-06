package com.example.shop_service.persistence.repository;

import com.example.shop_service.persistence.models.OrderProductEntity;
import com.example.shop_service.persistence.models.OrderProductKey;
import com.example.shop_service.persistence.projection.CompressedProductForOrderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity , OrderProductKey> {

    @Query("""
    SELECT 
        p.id AS id, 
        p.name AS name, 
        op.quantity AS quantity, 
        pr.price AS price  
    FROM OrderProductEntity op
    JOIN op.product p
    JOIN Product pr ON pr.id = p.id
    WHERE op.order.id = :orderId
""")
    List <CompressedProductForOrderProjection> findProductsByOrderId(@Param("orderId") UUID orderId);

}
