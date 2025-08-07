package com.example.lab.service;

import com.example.lab.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void process(Order order) {
        // Simulated business logic
        System.out.println("Processing order: " + order.getOrderId());
    }
}

