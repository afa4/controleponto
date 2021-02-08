package com.example.controleponto.entity.dto;

import com.example.controleponto.entity.TimeAllocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReportByMonthResp {
    @JsonProperty("mes")
    private String month;
    @JsonProperty("horasTrabalhadas")
    private Duration workedHours;
    @JsonProperty("horasExcedentes")
    private Duration positiveHours;
    @JsonProperty("horasDevidas")
    private Duration negativeHours;
    @JsonProperty("registros")
    private List<WorkdaySummary> workdays;

    @Data
    @Builder
    public static class WorkdaySummary {
        @JsonProperty("dia")
        private LocalDate day;
        @JsonProperty("horarios")
        private List<String> timeRegisters;
        @JsonProperty("alocacoes")
        private List<TimeAllocation> timeAllocations;
    }
}
