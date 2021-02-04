package com.example.controleponto.repository.mapper;

import com.example.controleponto.entity.Workday;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.time.LocalDateTime.parse;

@Component
public class WorkdayMapper implements RowMapper<Workday> {
    @Override
    public Workday mapRow(ResultSet resultSet, int i) throws SQLException {
        return Workday.builder()
                .id(resultSet.getLong("id"))
                .startedAt(parse(resultSet.getString("startedAt")))
                .pausedAt(parse(resultSet.getString("pausedAt")))
                .returnedAt(parse(resultSet.getString("returnedAt")))
                .endedAt(parse(resultSet.getString("endedAt")))
                .secondsWorked(resultSet.getInt("secondsWorked"))
                .build();
    }
}
