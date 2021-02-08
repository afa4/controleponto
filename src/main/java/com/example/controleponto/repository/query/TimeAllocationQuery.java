package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum TimeAllocationQuery {
    INSERT("timeallocation/insert-time_allocation.sql");

    private final String query;

    TimeAllocationQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}
