package com.example.controleponto.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportResp {
    private String month;
    private String workedHours;
    private String exceededHours;
    private String debtHours;
    private List<WorkdaySummary> workdays;
    private List<TimeAllocationSummary> timeAllocations;

    @Data
    public static class WorkdaySummary {
        private String day;
        private List<String> timeRegisters;
    }

    @Data
    public static class TimeAllocationSummary {
        private String projectName;
        private String time;
    }
}
