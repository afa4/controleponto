package com.example.controleponto.service;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.enumeration.TimeRegisterType;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.ForbiddenRegisterException;
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

    public void registerTime(LocalDateTime dateTime) {
        var workday = workdayRepository.findByDate(dateTime.toLocalDate());

        if (isNull(workday)) {
            workdayRepository.insert(dateTime);
        } else if (isDateTimeAlreadyInserted(workday, dateTime)) {
            throw new TimeRegisterExistsException();
        } else if (isDateTimeBeforePreviousRegister(workday, dateTime)) {
            throw new ForbiddenRegisterException();
        } else {
            insertTimeRegister(workday, dateTime);
        }
    }

    private boolean isDateTimeAlreadyInserted(Workday workday, LocalDateTime time) {
        return time.equals(workday.getStartedAt()) ||
                time.equals(workday.getPausedAt()) ||
                time.equals(workday.getReturnedAt()) ||
                time.equals(workday.getEndedAt());
    }

    private boolean isDateTimeBeforePreviousRegister(Workday workday, LocalDateTime time) {
        return time.isBefore(workday.getStartedAt()) ||
                (nonNull(workday.getPausedAt()) && time.isBefore(workday.getPausedAt())) ||
                (nonNull(workday.getReturnedAt()) && time.isBefore(workday.getReturnedAt()));
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
