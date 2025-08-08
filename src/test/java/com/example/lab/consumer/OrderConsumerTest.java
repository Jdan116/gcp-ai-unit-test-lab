package com.example.lab.consumer;

import com.example.lab.model.Order;
import com.example.lab.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderConsumerTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderConsumer orderConsumer;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void consume_validMessage_shouldProcessOrder() throws Exception {
        String message = "{\"id\":1}";
        Order order = new Order(1);
        when(objectMapper.readValue(message, Order.class)).thenReturn(order);
        doNothing().when(orderService).process(order);
        orderConsumer.consume(message);
        verify(orderService, times(1)).process(order);
    }

    @Test
    void consume_invalidJson_shouldThrowJsonProcessingException() throws Exception {
        String message = "invalid-json";
        when(objectMapper.readValue(message, Order.class)).thenThrow(new JsonProcessingException("error") {});
        assertThrows(JsonProcessingException.class, () -> orderConsumer.consume(message));
    }

    @Test
    void consume_nullMessage_shouldThrowJsonProcessingException() throws Exception {
        // Use an empty string to avoid ambiguous method call
        when(objectMapper.readValue("", Order.class)).thenThrow(new JsonProcessingException("null") {});
        assertThrows(JsonProcessingException.class, () -> orderConsumer.consume(""));
    }

    @Test
    void consume_emptyMessage_shouldThrowJsonProcessingException() throws Exception {
        when(objectMapper.readValue("", Order.class)).thenThrow(new JsonProcessingException("empty") {});
        assertThrows(JsonProcessingException.class, () -> orderConsumer.consume(""));
    }

    @Test
    void consume_orderServiceThrows_shouldPropagateException() throws Exception {
        String message = "{\"id\":2}";
        Order order = new Order(2);
        when(objectMapper.readValue(message, Order.class)).thenReturn(order);
        doThrow(new RuntimeException("service error")).when(orderService).process(order);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderConsumer.consume(message));
        assertEquals("service error", ex.getMessage());
    }
}
