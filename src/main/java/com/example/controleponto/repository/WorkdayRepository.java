package com.example.controleponto.repository;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.repository.mapper.WorkdayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static com.example.controleponto.repository.query.WorkdayQuery.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Repository
public class WorkdayRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final WorkdayMapper mapper;

    public WorkdayRepository(NamedParameterJdbcTemplate jdbcTemplate, WorkdayMapper mapper) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public void insert(LocalDateTime startedAt) {
        jdbcTemplate.update(INSERT.getQuery(), Map.of("startedAt", startedAt));
    }

    public Workday findByDate(LocalDate date) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_DATE.getQuery(), Map.of("referenceDate", date.toString()), mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(String query, Map<String, Object> params) {
        jdbcTemplate.update(query, params);
    }
}
