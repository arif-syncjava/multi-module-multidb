package com.arifsyncjava.database3.repository2;

import com.arifsyncjava.database3.model2.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
