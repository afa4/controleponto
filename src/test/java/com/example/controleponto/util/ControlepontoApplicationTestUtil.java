package com.example.controleponto.util;

import com.example.controleponto.entity.Workday;

import java.time.LocalDateTime;

public class ControlepontoApplicationTestUtil {
    public static Workday mockWorkday() {
        return Workday.builder()
                .id(1L)
                .startedAt(LocalDateTime.parse("2021-01-01T08:00:00"))
                .pausedAt(LocalDateTime.parse("2021-01-01T12:00:00"))
                .returnedAt(LocalDateTime.parse("2021-01-01T13:00:00"))
                .endedAt(LocalDateTime.parse("2021-01-01T17:00:00"))
                .secondsWorked(28800L)
                .build();
    }
}
