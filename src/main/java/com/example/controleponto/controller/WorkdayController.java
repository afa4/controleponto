package com.example.controleponto.controller;

import com.example.controleponto.entity.dto.RegisterTimeReq;
import com.example.controleponto.entity.dto.TimeAllocationReq;
import com.example.controleponto.service.TimeAllocationService;
import com.example.controleponto.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
public class WorkdayController {

    private final WorkdayService workdayService;
    private final TimeAllocationService timeAllocationService;

    @PostMapping("/batidas")
    public ResponseEntity<Object> registerTime(@RequestBody RegisterTimeReq registerTimeReq) {
        workdayService.registerTime(registerTimeReq.getTime());
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/alocacoes")
    public ResponseEntity<Object> registerTime(@RequestBody TimeAllocationReq timeAllocationReq) {
        timeAllocationService.allocateTime(timeAllocationReq.getDay(),
                timeAllocationReq.getDuration(),
                timeAllocationReq.getDescription());
        return ResponseEntity.status(CREATED).build();
    }
}
