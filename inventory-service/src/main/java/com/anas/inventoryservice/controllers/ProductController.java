package com.anas.inventoryservice.controllers;

import com.anas.inventoryservice.entities.Product;
import com.anas.inventoryservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
        private ProductRepository  productRepository;

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
        List<Product> productList(){
             return productRepository.findAll();
         }
                 ;

}
