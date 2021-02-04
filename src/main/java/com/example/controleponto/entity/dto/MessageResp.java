package com.example.controleponto.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageResp {
    @JsonProperty("mensagem")
    private String message;
}
