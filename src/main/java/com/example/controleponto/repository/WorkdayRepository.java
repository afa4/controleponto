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

@Slf4j
@Repository
public class WorkdayRepository {

    private final NamedParameterJdbcTemplate template;
    private final WorkdayMapper mapper;

    public WorkdayRepository(NamedParameterJdbcTemplate template, WorkdayMapper mapper) throws IOException {
        this.template = template;
        this.mapper = mapper;
    }

    public void insert(LocalDateTime startedAt) {
        template.update(INSERT.getQuery(), Map.of("startedAt", startedAt));
    }

    public void setTimeRegister(Workday workday, String dateTymeType, LocalDateTime dateTime) {
        String query = UPDATE_TIME_REGISTER.getQuery().replace(":moment", dateTymeType);
        template.update(query, Map.of("timestmp", dateTime.toString(), "id", workday.getId()));
    }

    public Workday findByDate(LocalDate date) {
        try {
            return template.queryForObject(FIND_BY_DATE.getQuery(), Map.of("referenceDate", date.toString()), mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
