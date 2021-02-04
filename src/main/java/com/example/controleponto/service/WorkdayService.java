package com.example.controleponto.service;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.repository.WorkdayRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static com.example.controleponto.repository.query.WorkdayQuery.UPDATE_TIME_REGISTER;
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

    private void insertTimeRegister(Workday workday, LocalDateTime time) {
        String moment;
        if (isNull(workday.getPausedAt())) {
            moment = "paused_at";
        } else if (isNull(workday.getReturnedAt())) {
            // calculate seconds worked
            moment = "returned_at";
        } else if (nonNull(workday.getEndedAt())) {
            throw new CompletedWorkdayException();
        } else {
            // calculate seconds worked
            moment = "ended_at";
        }
        String query = UPDATE_TIME_REGISTER.getQuery().replace(":moment", moment);
        log.info(query);
        workdayRepository.update(query, Map.of("timestmp", time.toString(), "id", workday.getId()));
    }
}
