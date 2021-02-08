package com.example.controleponto.repository.query;

import com.example.controleponto.util.SqlReaderUtil;
import lombok.Getter;

@Getter
public enum WorkdayQuery {

    INSERT("workday/insert-workday.sql"),
    UPDATE_WORKED_SECONDS("workday/update-workday-worked-seconds.sql"),
    UPDATE_TIME_REGISTER("workday/set-workday-time-register.sql"),
    FIND_BY_DATE("workday/select-workday-by-date.sql"),
    FIND_BY_YEAR_AND_MONTH("workday/select-workday-by-month-and-year.sql");

    private final String query;

    WorkdayQuery(String query) {
        this.query = SqlReaderUtil.read(query);
    }
}
