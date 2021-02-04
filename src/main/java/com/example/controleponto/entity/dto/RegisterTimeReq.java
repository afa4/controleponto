package com.example.controleponto.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterTimeReq {
    @JsonProperty("dataHora")
    private LocalDateTime time;
}
