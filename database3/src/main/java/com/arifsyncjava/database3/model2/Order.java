package com.arifsyncjava.database3.model2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "orders")
public class Order {
    @Id
    private Long id;
    private String name;
}
