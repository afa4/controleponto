package com.example.controleponto.entity.dto;

import com.example.controleponto.entity.summary.WorkdaySummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
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
    private List<WorkdaySummary> workdaySummaryList;
}
