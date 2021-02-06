package com.example.controleponto.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Workday {
    private Long id;
    private LocalDateTime startedAt;
    private LocalDateTime pausedAt;
    private LocalDateTime returnedAt;
    private LocalDateTime endedAt;
    private Long secondsWorked;
}
