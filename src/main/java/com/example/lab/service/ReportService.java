package com.example.lab.service;

import com.google.cloud.bigquery.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReportService {

    private final BigQuery bigQuery;

    public int fetchUserCount() {
        try {
            String query = "SELECT COUNT(*) AS count FROM `project.dataset.users`";
            TableResult result = bigQuery.query(QueryJobConfiguration.newBuilder(query).build());
            FieldValue val = result.iterateAll().iterator().next().get("count");
            return Integer.parseInt(val.getStringValue());
        } catch (Exception e) {
            throw new RuntimeException("BQ query failed", e);
        }
    }
}