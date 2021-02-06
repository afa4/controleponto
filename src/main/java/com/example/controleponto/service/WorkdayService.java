package com.example.controleponto.service;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.enumeration.TimeRegisterType;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.TimeRegisterExistsException;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class WorkdayService {

    private final WorkdayRepository workdayRepository;

    public void registerTime(LocalDateTime time) {
        var workday = workdayRepository.findByDate(time.toLocalDate());

        if (isNull(workday)) {
            workdayRepository.insert(time);
        } else if (isTimeAlreadyInserted(workday, time)) {
            throw new TimeRegisterExistsException();
        } else {
            insertTimeRegister(workday, time);
        }
    }

    private boolean isTimeAlreadyInserted(Workday workday, LocalDateTime time) {
        return workday.getStartedAt().equals(time) ||
                workday.getPausedAt().equals(time) ||
                workday.getReturnedAt().equals(time) ||
                workday.getEndedAt().equals(time);
    }

    private void insertTimeRegister(Workday workday, LocalDateTime dateTime) {
        TimeRegisterType registerType;
        boolean shouldIncrementWorkedSeconds = false;

        if (isNull(workday.getPausedAt())) {
            registerType = TimeRegisterType.PAUSED_AT;
            shouldIncrementWorkedSeconds = true;
            workday.setPausedAt(dateTime);
        } else if (isNull(workday.getReturnedAt())) {
            registerType = TimeRegisterType.RETURNED_AT;
        } else if (nonNull(workday.getEndedAt())) {
            throw new CompletedWorkdayException();
        } else {
            registerType = TimeRegisterType.ENDED_AT;
            shouldIncrementWorkedSeconds = true;
            workday.setEndedAt(dateTime);
        }
        workdayRepository.setTimeRegister(workday, registerType, dateTime);

        if (shouldIncrementWorkedSeconds) {
            incrementWorkedSecondsAsync(workday);
        }
    }

    private void incrementWorkedSecondsAsync(Workday workday) {
        CompletableFuture.runAsync(() -> {
            LocalDateTime first, last;
            if (nonNull(workday.getEndedAt())) {
                first = workday.getReturnedAt();
                last = workday.getEndedAt();
            } else {
                first = workday.getStartedAt();
                last = workday.getPausedAt();
            }
            var offset = ZoneOffset.UTC;
            var seconds = last.toEpochSecond(offset) - first.toEpochSecond(offset);

            workday.setSecondsWorked(workday.getSecondsWorked() + seconds);
            workdayRepository.updateWorkedSeconds(workday);
        });
    }
}
