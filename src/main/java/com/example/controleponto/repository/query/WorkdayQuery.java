package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum WorkdayQuery {

    INSERT("workday/insert-workday.sql"),
    FIND_BY_DATE("workday/select-workday-by-date.sql"),
    SET_TIME_REGISTER("workday/set-workday-time-register.sql");

    private final String query;

    WorkdayQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}
