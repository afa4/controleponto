package com.example.controleponto.entity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class TimeAllocationSummary {
    @JsonProperty("nomeProjeto")
    private String project;
    @JsonProperty("tempo")
    private Duration duration;
}
