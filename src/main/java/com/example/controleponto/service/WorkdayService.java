package com.example.controleponto.service;

import com.example.controleponto.repository.WorkdayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WorkdayService {

    private final WorkdayRepository workdayRepository;

    public void registerTime(LocalDateTime time) {

    }
}
