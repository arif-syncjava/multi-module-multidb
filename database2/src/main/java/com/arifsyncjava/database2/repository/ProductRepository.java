package com.arifsyncjava.database2.repository;

import com.arifsyncjava.database2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
