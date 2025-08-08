package com.example.lab.service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {
    @Mock BigQuery bigQuery;
    @Mock TableResult tableResult;
    @InjectMocks ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchUserCount_shouldReturnCount_whenQuerySucceeds() throws Exception {
        FieldValue fieldValue = mock(FieldValue.class);
        when(fieldValue.getStringValue()).thenReturn("42");
        Iterable<FieldValue> fieldValues = Collections.singletonList(fieldValue);
        when(tableResult.iterateAll()).thenReturn(Collections.singletonList(mock(com.google.cloud.bigquery.FieldValueList.class)));
        when(tableResult.iterateAll().iterator().next().get("count")).thenReturn(fieldValue);
        when(bigQuery.query(any(QueryJobConfiguration.class))).thenReturn(tableResult);
        int count = reportService.fetchUserCount();
        assertEquals(42, count);
    }

    @Test
    void fetchUserCount_shouldThrowRuntimeException_whenQueryFails() throws Exception {
        when(bigQuery.query(any(QueryJobConfiguration.class))).thenThrow(new RuntimeException("bq error"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> reportService.fetchUserCount());
        assertTrue(ex.getMessage().contains("BQ query failed"));
    }

    @Test
    void fetchUserCount_shouldThrowRuntimeException_whenResultIsEmpty() throws Exception {
        when(tableResult.iterateAll()).thenReturn(Collections.emptyList());
        when(bigQuery.query(any(QueryJobConfiguration.class))).thenReturn(tableResult);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> reportService.fetchUserCount());
        assertTrue(ex.getMessage().contains("BQ query failed"));
    }
}

