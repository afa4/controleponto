package com.example.controleponto.repository.mapper;

import com.example.controleponto.entity.dto.TimeAllocationSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TimeAllocationSummaryJsonMapper implements RowMapper<Map.Entry<Long, TimeAllocationSummary>> {

    private final ObjectMapper objectMapper;

    @Override
    public Map.Entry<Long, TimeAllocationSummary> mapRow(ResultSet resultSet, int i)
            throws SQLException, JsonProcessingException {
        return new Map.entry(
                resultSet.getLong("workday_id"),
                objectMapper.readValue(resultSet.getString("timeAllocationSummary"), TimeAllocationSummary.class)
        );
    }
}
