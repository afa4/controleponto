package com.example.controleponto.repository.mapper;

import com.example.controleponto.entity.Workday;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class WorkdayMapper implements RowMapper<Workday> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Workday mapRow(ResultSet resultSet, int i) throws SQLException {
        return Workday.builder()
                .id(resultSet.getLong("id"))
                .startedAt(parse(resultSet.getString("started_at")))
                .pausedAt(parse(resultSet.getString("paused_at")))
                .returnedAt(parse(resultSet.getString("returned_at")))
                .endedAt(parse(resultSet.getString("ended_at")))
                .secondsWorked(resultSet.getInt("seconds_worked"))
                .build();
    }

    private LocalDateTime parse(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(timeStr, formatter);
    }
}
