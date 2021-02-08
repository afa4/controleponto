package com.example.controleponto.repository;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.repository.mapper.TimeAllocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.controleponto.repository.query.TimeAllocationQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TimeAllocationRepository {
    private final NamedParameterJdbcTemplate template;
    private final TimeAllocationMapper mapper;

    public void insert(Long workdayId, String description, Long secondsAllocated) {
        template.update(INSERT.getQuery(), Map.of("workdayId", workdayId,
                "description", description,
                "secondsAllocated", secondsAllocated));
    }

    public List<TimeAllocation> findAllByWorkdayId(Long workdayId) {
        return template.query(FIND_BY_WORKDAY_ID.getQuery(), Map.of("workdayId", workdayId), mapper);
    }

    public void updateSecondsAllocatedById(Long timeAllocationId, Long secondsAllocated) {
        template.update(UPDATE_SECONDS_ALLOCATED_BY_ID.getQuery(), Map.of("id", timeAllocationId,
                "secondsAllocated", secondsAllocated));
    }

    public Map<Long, List<TimeAllocation>> findAllTimeAllocationsGroupedByWorkdaysIds(List<Long> workdaysIds) {
        if(workdaysIds.isEmpty())
            return Map.of();

        var map = new HashMap<Long, List<TimeAllocation>>();

        template.query(FIND_BY_WORKDAY_ID_IN.getQuery(), Map.of("ids", workdaysIds), mapper)
                .forEach(timeAllocation -> {
                    var key = map.get(timeAllocation.getWorkdayId());
                    if (key == null) {
                        List<TimeAllocation> allocations = new ArrayList<>();
                        allocations.add(timeAllocation);
                        map.put(timeAllocation.getWorkdayId(), allocations);
                    } else {
                        map.get(timeAllocation.getWorkdayId()).add(timeAllocation);
                    }
                });

        return map;
    }

}
