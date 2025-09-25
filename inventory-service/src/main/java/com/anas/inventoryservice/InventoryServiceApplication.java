package com.anas.inventoryservice;

import com.anas.inventoryservice.entities.Product;
import com.anas.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product product1 = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .price(12.0)
                    .quantity(1200)
                    .name("Product 1")

            .build();
            Product product2 =Product.builder()
                    .id(UUID.randomUUID().toString())
                    .price(12.0)
                    .quantity(1000)
                    .name("Product 2")
                    .build();
            Product product3 =Product.builder()
                    .id(UUID.randomUUID().toString())
                    .price(10.0)
                    .quantity(1400)
                    .name("Product 3")
                    .build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        };
    }
}
