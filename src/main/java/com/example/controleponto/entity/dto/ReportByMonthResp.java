package com.example.controleponto.entity.dto;

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
    @JsonProperty("alocacoes")
    private List<TimeAllocationSummary> timeAllocations;

    @Data
    @Builder
    public static class WorkdaySummary {
        @JsonProperty("dia")
        private LocalDate day;
        @JsonProperty("horarios")
        private List<String> timeRegisters;
    }

    @Data
    @Builder
    public static class TimeAllocationSummary {
        @JsonProperty("nomeProjeto")
        private String description;
        @JsonProperty("tempo")
        private Duration secondsAllocated;
    }

}
