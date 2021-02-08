package com.example.controleponto.controller;

import com.example.controleponto.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/folhas-de-ponto/{mes}")
    public ResponseEntity getWorkdayReportByMonth(@PathVariable("mes") String yearMonth) {
        var yearMonthArray = yearMonth.split("-");

        if (yearMonthArray.length == 2) {
            try {
                var year = Integer.parseInt(yearMonthArray[0]);
                var month = Integer.parseInt(yearMonthArray[1]);

                return ResponseEntity.ok(reportService.getReportByMonth(year, month));
            } catch (NumberFormatException ignored) {
            }
        }
        throw new RuntimeException();
    }
}
