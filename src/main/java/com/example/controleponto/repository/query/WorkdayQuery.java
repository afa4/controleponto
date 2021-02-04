package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum WorkdayQuery {

    INSERT("workday/insert-workday.sql"),
    FIND_BY_DATE("workday/select-workday-by-date.sql"),
    UPDATE_TIME_REGISTER("workday/update-workday-moment.sql");

    private final String query;

    WorkdayQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}
