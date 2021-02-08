package com.example.controleponto.service;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.dto.ReportByMonthResp;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final WorkdayRepository workdayRepository;
    private final TimeAllocationRepository timeAllocationRepository;
    private final Long maximumWorkdaySeconds;

    public ReportService(WorkdayRepository workdayRepository,
                         TimeAllocationRepository timeAllocationRepository,
                         @Value("${report.maximum-workday-hours}") Long maximumWorkdayHours) {
        this.workdayRepository = workdayRepository;
        this.timeAllocationRepository = timeAllocationRepository;
        this.maximumWorkdaySeconds = asSeconds(maximumWorkdayHours);
    }

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
                .negativeHours(Duration.ofSeconds(calculateNegativeHours(workdays))) // TODO: Build dto workdaySummary
                .workdays(workdays.stream()
                        .map(workday -> ReportByMonthResp.WorkdaySummary.builder()
                                        .timeAllocations(timeAllocationsByWorkday.get(workday.getId()))
                                        .build())
                        .collect(Collectors.toList())
                ).build();
    }

    private Long calculateWorkedHours(List<Workday> workdays) {
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .reduce(0L, Long::sum);
    }

    private Long calculatePositiveHours(List<Workday> workdays) {
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .filter(secondsWorked -> secondsWorked >= maximumWorkdaySeconds)
                .map(secondsWorked -> secondsWorked - maximumWorkdaySeconds)
                .reduce(0L, Long::sum);
    }

    private Long calculateNegativeHours(List<Workday> workdays) {
        return workdays.stream()
                .map(Workday::getSecondsWorked)
                .filter(secondsWorked -> secondsWorked < maximumWorkdaySeconds)
                .map(secondsWorked -> maximumWorkdaySeconds - secondsWorked)
                .reduce(0L, Long::sum);
    }

    private Long asSeconds(Long hours) {
        return hours * 60 * 60;
    }
}
