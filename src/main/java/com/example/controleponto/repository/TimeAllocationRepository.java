package com.example.controleponto.repository;

import com.example.controleponto.repository.mapper.WorkdayMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.example.controleponto.repository.query.TimeAllocationQuery.INSERT;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TimeAllocationRepository {
    private final NamedParameterJdbcTemplate template;
    private final WorkdayMapper mapper;

    public void insert(Long workdayId, String description, Long secondsAllocated) {
        template.update(INSERT.getQuery(), Map.of("workdayId", workdayId,
                "description", description,
                "secondsAllocated", secondsAllocated));
    }

}
