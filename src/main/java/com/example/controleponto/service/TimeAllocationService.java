package com.example.controleponto.service;

import com.example.controleponto.entity.TimeAllocation;
import com.example.controleponto.exception.CantAllocateTimeException;
import com.example.controleponto.exception.TimeToAllocateBiggerThanWorkedTimeException;
import com.example.controleponto.repository.TimeAllocationRepository;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TimeAllocationService {

    private final WorkdayRepository workdayRepository;
    private final TimeAllocationRepository timeAllocationRepository;

    public void allocateTime(LocalDate date, Duration duration, String description) {
        var workday = workdayRepository.findByDate(date);
        if (nonNull(workday) && (nonNull(workday.getPausedAt()) || nonNull(workday.getEndedAt()))) {
            var timeToAllocate = duration.getSeconds();

            var timeAllocations = timeAllocationRepository
                    .findAllByWorkdayId(workday.getId());

            var currentAllocation = timeAllocations.stream()
                    .map(TimeAllocation::getSecondsAllocated)
                    .reduce(0L, Long::sum);

            if (currentAllocation + timeToAllocate > workday.getSecondsWorked()) {
                throw new TimeToAllocateBiggerThanWorkedTimeException();
            }

            saveTimeAllocation(description, workday, timeToAllocate, timeAllocations);
        } else {
            throw new CantAllocateTimeException();
        }
    }

    private void saveTimeAllocation(String description, com.example.controleponto.entity.Workday workday, long timeToAllocate, java.util.List<TimeAllocation> timeAllocations) {
        var sameDescriptionAllocation = timeAllocations.stream()
                .filter(timeAllocation -> timeAllocation.getDescription().equals(description))
                .findFirst()
                .orElse(null);

        if (nonNull(sameDescriptionAllocation)) {
            timeAllocationRepository.updateSecondsAllocatedById(sameDescriptionAllocation.getId(),
                    sameDescriptionAllocation.getSecondsAllocated() + timeToAllocate);
        } else {
            timeAllocationRepository.insert(workday.getId(), description, timeToAllocate);
        }
    }

}
