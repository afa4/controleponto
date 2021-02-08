package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum TimeAllocationQuery {
    INSERT("timeallocation/insert-time_allocation.sql"),
    FIND_BY_WORKDAY_ID("timeallocation/select-time_allocation-by-workday-id.sql");

    private final String query;

    TimeAllocationQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}