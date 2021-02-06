package com.example.controleponto.service;

import com.example.controleponto.repository.WorkdayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkdayServiceTest {

    @Mock
    WorkdayRepository workdayRepository;

    @InjectMocks
    WorkdayService workdayService;

    @Test
    public void whenRegisterTimeShouldSearchForWorkdayByDate() {
        when(workdayRepository.findByDate(any()))
                .thenReturn(null);

        workdayService.registerTime(LocalDateTime.parse("2020-01-01T08:00:00"));

        verify(workdayRepository).findByDate(eq(LocalDate.parse("2020-01-01")));
    }
}
