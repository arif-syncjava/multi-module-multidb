package com.arifsyncjava.database2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter  @Setter
@NoArgsConstructor
@Entity (name = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
}
