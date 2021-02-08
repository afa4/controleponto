package com.example.controleponto.service;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.dto.ReportByMonthResp;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final WorkdayRepository workdayRepository;
    private final TimeAllocationRepository timeAllocationRepository;

    @Value("${report.maximum-workday-hours}")
    private final Long maximumWorkdayHours;

    public ReportByMonthResp getReportByMonth(int year, int month) {
        var workDays = workdayRepository.findByYearAndMonth(year, month);

        var timeAllocationsByWorkday = timeAllocationRepository
                .findAllTimeAllocationsGroupedByWorkdaysIds(
                        workDays.stream()
                                .map(Workday::getId)
                                .collect(Collectors.toList()));


        return buildReport(year, month, workDays, timeAllocationsByWorkday);
    }

    private ReportByMonthResp buildReport(int year,
                                          int month,
                                          List<Workday> workdays,
                                          Map<Long, List<TimeAllocation>> timeAllocationsByWorkday) {

        return ReportByMonthResp.builder()
                .month(year + "-" + (Integer.toString(month).length() == 1 ? "0" + month : month))
                .workedHours(Duration.ofSeconds(calculateWorkedHours(workdays)))
                .positiveHours(Duration.ofSeconds(calculatePositiveHours(workdays)))
                .negativeHours(Duration.ofSeconds(calculateNegativeHours(workdays)))
                .build();
    }

    private Long calculateWorkedHours(List<Workday> workdays) {
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .reduce(0L, Long::sum);
    }

    private Long calculatePositiveHours(List<Workday> workdays) {
        Long maximumSecondsToWork = asSeconds(maximumWorkdayHours);
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .filter(secondsWorked -> secondsWorked >= maximumSecondsToWork)
                .map(secondsWorked -> secondsWorked - maximumSecondsToWork)
                .reduce(0L, Long::sum);
    }

    private Long calculateNegativeHours(List<Workday> workdays) {
        Long maximumSecondsToWork = asSeconds(maximumWorkdayHours);
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .filter(secondsWorked -> secondsWorked < maximumSecondsToWork)
                .map(secondsWorked -> maximumSecondsToWork - secondsWorked)
                .reduce(0L, Long::sum);
    }

    private Long asSeconds(Long hours) {
        return hours * 60 * 60;
    }
}
