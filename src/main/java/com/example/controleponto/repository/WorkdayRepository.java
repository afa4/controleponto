package com.example.controleponto.repository;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.enumeration.TimeRegisterType;
import com.example.controleponto.repository.mapper.WorkdayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static com.example.controleponto.repository.query.WorkdayQuery.*;

@Slf4j
@Repository
public class WorkdayRepository {

    private final NamedParameterJdbcTemplate template;
    private final WorkdayMapper mapper;

    public WorkdayRepository(NamedParameterJdbcTemplate template, WorkdayMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void insert(LocalDateTime startedAt) {
        template.update(INSERT.getQuery(), Map.of("startedAt", startedAt));
    }

    public void setTimeRegister(Workday workday, TimeRegisterType type, LocalDateTime dateTime) {
        String query = SET_TIME_REGISTER.getQuery().replace(":moment", type.getValue());
        template.update(query,
                Map.of("timestmp", dateTime.toString(),
                        "id", workday.getId()));
    }

    public Workday findByDate(LocalDate date) {
        try {
            return template.queryForObject(FIND_BY_DATE.getQuery(), Map.of("referenceDate", date.toString()), mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateWorkedSeconds(Workday workday) {
        String query = UPDATE_WORKED_SECONDS.getQuery();
        template.update(query,
                Map.of("secondsWorked", workday.getSecondsWorked(),
                        "id", workday.getId()));

    }
}
