package com.example.controleponto.controller;

import com.example.controleponto.entity.dto.RegisterTimeReq;
import com.example.controleponto.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WorkdayController {

    private final WorkdayService workdayService;

    @PostMapping("/batidas")
    public ResponseEntity<Object> registerTime(@RequestBody RegisterTimeReq registerTimeReq) {
        workdayService.registerTime(registerTimeReq.getTime());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
