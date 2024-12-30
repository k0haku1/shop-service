package com.example.shop_service.sсheduler;
import com.example.shop_service.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@RequiredArgsConstructor
@ConditionalOnExpression("${spring.app.scheduler.enabled:true} && !${spring.app.scheduler.optimisation:false}")
@Component
public class PriceSсheduler {

    private final ProductRepository productRepository;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void updatePrice() {

        System.out.println("updating...");

        productRepository.findAll().forEach(product -> {

            BigDecimal currentPrice = product.getPrice();
            BigDecimal newPrice = currentPrice.add(BigDecimal.valueOf(10.1));
            product.setPrice(newPrice);

        });

        productRepository.saveAll(productRepository.findAll());

        System.out.println("done");
    }

}
