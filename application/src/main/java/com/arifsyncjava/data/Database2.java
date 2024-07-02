package com.arifsyncjava.data;

import com.arifsyncjava.database2.model.Product;
import com.arifsyncjava.database2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(2)
public class Database2 implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Product product = new Product();
        product.setId(123L);
        product.setName("product");
        product.setPrice(12.50);
        productRepository.save(product);

    }
}
