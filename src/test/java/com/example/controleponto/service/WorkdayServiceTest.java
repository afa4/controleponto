package com.example.controleponto.service;

import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.ForbiddenRegisterException;
import com.example.controleponto.repository.WorkdayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.controleponto.util.ControlepontoApplicationTestUtil.mockOpenWorkday;
import static com.example.controleponto.util.ControlepontoApplicationTestUtil.mockWorkday;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void whenRegisterTimeThenShouldSearchForWorkdayByDate() {
        when(workdayRepository.findByDate(any()))
                .thenReturn(null);

        workdayService.registerTime(LocalDateTime.parse("2020-01-01T08:00:00"));

        verify(workdayRepository).findByDate(eq(LocalDate.parse("2020-01-01")));
    }

    @Test
    public void whenRegisterTimeIfFetchedWorkdayIsFullThanShouldThrowException() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkday());

        assertThrows(CompletedWorkdayException.class, () ->
                workdayService.registerTime(LocalDateTime.parse("2021-01-01T16:00:00")));
    }

    @Test
    public void whenRegisterTimeIfParamIsBeforePreviousDateTimeRegisterInTSameWorkdayThanShouldThrowException() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockOpenWorkday());

        List.of("2021-01-01T07:50:00", "2021-01-01T09:00:00", "2021-01-01T12:30:00")
                .forEach(dateTime ->
                        assertThrows(ForbiddenRegisterException.class,
                                () -> workdayService.registerTime(LocalDateTime.parse(dateTime)))
                );
    }

}
