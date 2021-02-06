package com.example.controleponto.service;

import com.example.controleponto.entity.TimeRegisterType;
import com.example.controleponto.entity.Workday;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        } else {
            insertTimeRegister(workday, time);
        }
    }

    private void insertTimeRegister(Workday workday, LocalDateTime dateTime) {
        TimeRegisterType registerType;
        if (isNull(workday.getPausedAt())) {
            registerType = TimeRegisterType.PAUSED_AT;
        } else if (isNull(workday.getReturnedAt())) {
            // calculate seconds worked
            registerType = TimeRegisterType.RETURNED_AT;
        } else if (nonNull(workday.getEndedAt())) {
            throw new CompletedWorkdayException();
        } else {
            // calculate seconds worked
            registerType = TimeRegisterType.ENDED_AT;
        }
        workdayRepository.setTimeRegister(workday, registerType, dateTime);
    }
}
