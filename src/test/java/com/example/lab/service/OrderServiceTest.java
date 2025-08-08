//package com.example.lab.service;
//
//import com.example.lab.model.Order;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OrderServiceTest {
//    private final OrderService orderService = new OrderService();
//
//    @Test
//    void process_validOrder_shouldNotThrow() {
//        Order order = new Order(1);
//        assertDoesNotThrow(() -> orderService.process(order));
//    }
//
//    @Test
//    void process_nullOrder_shouldThrowNullPointerException() {
//        assertThrows(NullPointerException.class, () -> orderService.process(null));
//    }
//
//    @Test
//    void process_orderWithNegativeId_shouldNotThrow() {
//        Order order = new Order(-100);
//        assertDoesNotThrow(() -> orderService.process(order));
//    }
//
//    @Test
//    void process_orderWithZeroId_shouldNotThrow() {
//        Order order = new Order(0);
//        assertDoesNotThrow(() -> orderService.process(order));
//    }
//
//    @Test
//    void process_orderWithMaxIntId_shouldNotThrow() {
//        Order order = new Order(Integer.MAX_VALUE);
//        assertDoesNotThrow(() -> orderService.process(order));
//    }
//
//    @Test
//    void process_orderWithMinIntId_shouldNotThrow() {
//        Order order = new Order(Integer.MIN_VALUE);
//        assertDoesNotThrow(() -> orderService.process(order));
//    }
//}
//
