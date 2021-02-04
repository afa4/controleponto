package com.example.controleponto.service;

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
        String dateTimeType;
        if (isNull(workday.getPausedAt())) {
            dateTimeType = "paused_at";
        } else if (isNull(workday.getReturnedAt())) {
            // calculate seconds worked
            dateTimeType = "returned_at";
        } else if (nonNull(workday.getEndedAt())) {
            throw new CompletedWorkdayException();
        } else {
            // calculate seconds worked
            dateTimeType = "ended_at";
        }
        workdayRepository.setTimeRegister(workday, dateTimeType, dateTime);
    }
}
