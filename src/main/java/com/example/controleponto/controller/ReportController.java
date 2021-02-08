package com.example.controleponto.controller;

import com.example.controleponto.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/folhas-de-ponto/{mes}")
    public ResponseEntity getWorkdayReportByMonth(@PathParam("mes") String yearMonth) {
        var yearMonthArray = yearMonth.split("-");

        if (yearMonthArray.length == 2) {
            try {
                var year = Integer.parseInt(yearMonthArray[0]);
                var month = Integer.parseInt(yearMonthArray[1]);

                return ResponseEntity.ok(reportService.getReportByMonth(year, month));
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }
}
