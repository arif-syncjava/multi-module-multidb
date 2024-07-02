package com.arifsyncjava.database2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
}
