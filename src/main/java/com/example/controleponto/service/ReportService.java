package com.example.controleponto.service;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.dto.ReportByMonthResp;
import com.example.controleponto.entity.summary.TimeAllocationSummary;
import com.example.controleponto.entity.summary.WorkdaySummary;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.controleponto.util.LocalDateTimeUtil.onlyTime;

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
        var workdays = workdayRepository.findByYearAndMonth(year, month);

        var workdaysIds = workdays.stream()
                .map(Workday::getId)
                .collect(Collectors.toList());

        var timeAllocationsByWorkday = timeAllocationRepository
                .findAllTimeAllocationsGroupedByWorkdaysIds(workdaysIds);

        return buildReport(year, month, workdays, timeAllocationsByWorkday);
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
                .workdaySummaryList(buildWorkdaySummaryList(workdays, timeAllocationsByWorkday))
                .build();
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

    private List<WorkdaySummary> buildWorkdaySummaryList(List<Workday> workdays,
                                                         Map<Long, List<TimeAllocation>> timeAllocationsByWorkday) {
        return workdays.stream().map(workday ->
                WorkdaySummary.builder()
                        .day(workday.getStartedAt().toLocalDate())
                        .timeRegisters(timeRegistersAsListOfTime(workday))
                        .timeAllocations(buildTimeAllocationSummaryList(timeAllocationsByWorkday.get(workday.getId())))
                        .build())
                .collect(Collectors.toList());
    }

    private List<String> timeRegistersAsListOfTime(Workday workday) {
        return List.of(onlyTime(workday.getStartedAt()),
                onlyTime(workday.getPausedAt()),
                onlyTime(workday.getReturnedAt()),
                onlyTime(workday.getEndedAt()));
    }

    private List<TimeAllocationSummary> buildTimeAllocationSummaryList(List<TimeAllocation> timeAllocations) {
        return timeAllocations.stream().map(timeAllocation ->
                TimeAllocationSummary.builder()
                        .project(timeAllocation.getDescription())
                        .duration(Duration.ofSeconds(timeAllocation.getSecondsAllocated()))
                        .build())
                .collect(Collectors.toList());
    }
}
