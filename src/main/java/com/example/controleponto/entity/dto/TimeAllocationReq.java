package com.example.controleponto.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
public class TimeAllocationReq {
    @JsonProperty("dia")
    private LocalDate day;
    @JsonProperty("tempo")
    private Duration duration;
    @JsonProperty("nomeProjeto")
    private String description;
}
