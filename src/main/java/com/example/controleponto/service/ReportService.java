package com.example.controleponto.service;

import com.example.controleponto.entity.dto.ReportByMonthResp;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final WorkdayRepository workdayRepository;
    private final TimeAllocationRepository timeAllocationRepository;

    public ReportByMonthResp getReportByMonth() {
        return null;
    }
}
