package com.example.controleponto.entity;

import lombok.Data;

@Data
public class Workday {
    private Long id;
    private Long startedAt;
    private String pausedAt;
    private String returnedAt;
    private String endedAt;
    private Integer secondsWorked;
}
