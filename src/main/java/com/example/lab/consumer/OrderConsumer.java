package com.example.lab.consumer;

import com.example.lab.model.Order;
import com.example.lab.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public void consume(String message) throws JsonProcessingException {
        Order order = objectMapper.readValue(message, Order.class);
        orderService.process(order);
    }
}