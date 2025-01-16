package com.example.shop_service.sсheduler;

import com.example.shop_service.annotation.ExecutionTime;
import com.example.shop_service.persistence.repository.ProductRepositoryJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${spring.app.scheduler.enabled:true} && ${spring.app.scheduler.optimisation:true}")
public class OptimisationPriceScheduler {

    private final ProductRepositoryJdbc productRepositoryJdbc;

    @Value("${spring.app.price}")
    private BigDecimal priceIncrementPercentage;

    @ExecutionTime
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void updatePrice() {

        System.out.println("0.updating...");

        try {
            System.out.println("Updating prices with increment percentage: " + priceIncrementPercentage + " %");

            int updatedRows = productRepositoryJdbc.updatePrices(priceIncrementPercentage);
            String result = "Updated rows: " + updatedRows + " at " + LocalDateTime.now();

            writeLogToFile(result);

            System.out.println(result);

        } catch (Exception e) {

            String errorLog = "Error occurred: " + e.getMessage() + " at " + LocalDateTime.now();
            writeLogToFile(errorLog);
            System.err.println(errorLog);
            e.printStackTrace();

        }

        System.out.println("done");
    }

    private void writeLogToFile(String logMessage) {

        String filePath = "scheduler-log.txt";

        try {
            Files.write(
                    Paths.get(filePath),
                    (logMessage + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {

            System.err.println("Failed to write log to file: " + e.getMessage());

        }
    }
}