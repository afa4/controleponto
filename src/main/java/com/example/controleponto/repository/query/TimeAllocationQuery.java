package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum TimeAllocationQuery {
    INSERT("timeallocation/insert-time_allocation.sql"),
    UPDATE_SECONDS_ALLOCATED_BY_ID("timeallocation/update-time_allocation-seconds-by-id.sql"),
    FIND_BY_WORKDAY_ID("timeallocation/select-time_allocation-by-workday-id.sql"),
    FIND_BY_WORKDAY_ID_IN("timeallocation/select-time_allocation-grouped-by-workday-id-in.sql");

    private final String query;

    TimeAllocationQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}
