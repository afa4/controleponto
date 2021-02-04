package com.example.controleponto.repository;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Repository
public class WorkdayRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT;

    public WorkdayRepository(NamedParameterJdbcTemplate jdbcTemplate) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.INSERT = SqlReaderUtil.read("workday/insert-workday.sql");
    }

    public void insert(LocalDateTime startedAt) {
        log.info("New workday on " + startedAt.toLocalDate().toString());

        jdbcTemplate.update(INSERT, Map.of("startedAt", startedAt));
    }
}
