package com.example.controleponto.service;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.entity.dto.ReportByMonthResp;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final WorkdayRepository workdayRepository;
    private final TimeAllocationRepository timeAllocationRepository;

    public ReportByMonthResp getReportByMonth(String year, String month) {
        var workDays = workdayRepository.findByYearAndMonth(year, month);

        return null;
    }

    private Map<Long, TimeAllocation> fetchTimeAllocationsByWorkdaysIds(List<Long> workdaysIds) {
        return null;
    }
}
