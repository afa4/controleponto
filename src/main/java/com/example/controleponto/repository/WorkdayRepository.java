package com.example.controleponto.repository;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.repository.mapper.WorkdayMapper;
import com.example.controleponto.util.SqlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Repository
public class WorkdayRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final WorkdayMapper mapper;

    private final String INSERT;
    private final String FIND_BY_DATE;

    public WorkdayRepository(NamedParameterJdbcTemplate jdbcTemplate, WorkdayMapper mapper) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.INSERT = SqlReaderUtil.read("workday/insert-workday.sql");
        this.FIND_BY_DATE = SqlReaderUtil.read("workday/select-workday-by-date.sql");
    }

    public void insert(LocalDateTime startedAt) {
        jdbcTemplate.update(INSERT, Map.of("startedAt", startedAt));
    }

    public Workday findOneByDate(LocalDate date) {
        return jdbcTemplate.queryForObject(FIND_BY_DATE, Map.of("referenceDate", date.toString()), mapper);
    }
}
