package com.example.controleponto.service;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.TimeRegisterExistsException;
import com.example.controleponto.exception.TimeRegisterForbiddenException;
import com.example.controleponto.repository.WorkdayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.controleponto.util.ControlepontoApplicationTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkdayServiceTest {

    @Mock
    WorkdayRepository workdayRepository;

    @InjectMocks
    WorkdayService workdayService;

    @Test
    public void shouldSearchForWorkdayByDate() {
        when(workdayRepository.findByDate(any()))
                .thenReturn(null);

        workdayService.registerTime(LocalDateTime.parse("2020-01-01T08:00:00"));

        verify(workdayRepository).findByDate(eq(LocalDate.parse("2020-01-01")));
    }

    @Test
    public void shouldThrowException_whenFetchedWorkdayIsFull() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkday());

        assertThrows(CompletedWorkdayException.class, () ->
                workdayService.registerTime(LocalDateTime.parse("2021-01-01T16:00:00")));
    }

    @Test
    public void shouldThrowException_whenParamIsBeforePreviousDateTimeRegisterInTheSameWorkday() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockOpenWorkday());

        List.of("2021-01-01T07:50:00", "2021-01-01T09:00:00", "2021-01-01T12:30:00")
                .forEach(dateTime ->
                        assertThrows(TimeRegisterForbiddenException.class,
                                () -> workdayService.registerTime(LocalDateTime.parse(dateTime)))
                );
    }

    @Test
    public void shouldThrowException_whenParamIsAlreadyRegistered() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockOpenWorkday());

        List.of("2021-01-01T08:00:00", "2021-01-01T12:00:00", "2021-01-01T13:00:00")
                .forEach(dateTime ->
                        assertThrows(TimeRegisterExistsException.class,
                                () -> workdayService.registerTime(LocalDateTime.parse(dateTime)))
                );
    }

    @Test
    public void shouldIncrementSecondsWorked_whenParamIsAPausedAtRegister() throws InterruptedException {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockStartedWorkday());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        doNothing().when(workdayRepository).updateWorkedSeconds(workdayCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T12:05:00"));

        Thread.sleep(500);

        assertEquals(14700L, workdayCaptor.getValue().getSecondsWorked());
    }

    @Test
    public void shouldIncrementSecondsWorked_whenParamIsAnEndedAtRegister() throws InterruptedException {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockOpenWorkday());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        doNothing().when(workdayRepository).updateWorkedSeconds(workdayCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T16:05:00"));

        Thread.sleep(500);

        assertEquals(25500L, workdayCaptor.getValue().getSecondsWorked());
    }

}
