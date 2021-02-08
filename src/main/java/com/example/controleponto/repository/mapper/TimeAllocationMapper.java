package com.example.controleponto.repository.mapper;

import com.example.controleponto.entity.TimeAllocation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TimeAllocationMapper implements RowMapper<TimeAllocation> {
    @Override
    public TimeAllocation mapRow(ResultSet resultSet, int i) throws SQLException {
        return TimeAllocation.builder()
                .id(resultSet.getLong("id"))
                .workdayId(resultSet.getLong("workday_id"))
                .description(resultSet.getString("description"))
                .secondsAllocated(resultSet.getLong("seconds_allocated"))
                .build();
    }
}
