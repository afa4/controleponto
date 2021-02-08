package com.example.controleponto.entity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class WorkdaySummary {
    @JsonProperty("dia")
    private LocalDate day;
    @JsonProperty("horarios")
    private List<String> timeRegisters;
    @JsonProperty("alocacoes")
    private List<TimeAllocationSummary> timeAllocations;
}
