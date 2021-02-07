package com.example.controleponto.service;

import com.example.controleponto.entity.Workday;
import com.example.controleponto.entity.enumeration.TimeRegisterType;
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
    public void shouldInsertWorkday_whenFetchedWorkdayIsNull() {
        when(workdayRepository.findByDate(any()))
                .thenReturn(null);

        workdayService.registerTime(LocalDateTime.parse("2020-01-01T08:00:00"));

        verify(workdayRepository).insert(eq(LocalDateTime.parse("2020-01-01T08:00:00")));
    }

    @Test
    public void shouldThrowCompletedWorkdayException_whenFetchedWorkdayIsCompleted() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkday());

        assertThrows(CompletedWorkdayException.class, () ->
                workdayService.registerTime(LocalDateTime.parse("2021-01-01T16:00:00")));
    }

    @Test
    public void shouldThrowTimeRegisterForbiddenException_whenParamIsBeforePreviousDateTimeRegisterInTheSameWorkday() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithReturnedAt());

        List.of("2021-01-01T07:50:00", "2021-01-01T09:00:00", "2021-01-01T12:30:00")
                .forEach(dateTime ->
                        assertThrows(TimeRegisterForbiddenException.class,
                                () -> workdayService.registerTime(LocalDateTime.parse(dateTime)))
                );
    }

    @Test
    public void shouldThrowTimeRegisterExistsException_whenParamIsAlreadyRegistered() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithReturnedAt());

        List.of("2021-01-01T08:00:00", "2021-01-01T12:00:00", "2021-01-01T13:00:00")
                .forEach(dateTime ->
                        assertThrows(TimeRegisterExistsException.class,
                                () -> workdayService.registerTime(LocalDateTime.parse(dateTime)))
                );
    }

    @Test
    public void shouldUpdateWorkdayPausedAt_whenFetchedWorkdayPausedAtIsNull() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithStartedAt());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        var registerTypeCaptor = ArgumentCaptor.forClass(TimeRegisterType.class);
        doNothing().when(workdayRepository).setTimeRegister(workdayCaptor.capture(), registerTypeCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T12:05:10"));

        assertEquals("2021-01-01T12:05:10", workdayCaptor.getValue().getPausedAt().toString());
        assertEquals(TimeRegisterType.PAUSED_AT, registerTypeCaptor.getValue());
    }

    @Test
    public void shouldUpdateWorkdayReturnedAt_whenFetchedWorkdayReturnedAtIsNull() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithPausedAt());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        var registerTypeCaptor = ArgumentCaptor.forClass(TimeRegisterType.class);
        doNothing().when(workdayRepository).setTimeRegister(workdayCaptor.capture(), registerTypeCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T13:30:54"));

        assertEquals("2021-01-01T13:30:54", workdayCaptor.getValue().getReturnedAt().toString());
        assertEquals(TimeRegisterType.RETURNED_AT, registerTypeCaptor.getValue());
    }

    @Test
    public void shouldUpdateWorkdayEndedAt_whenFetchedWorkdayEndedAtIsNull() {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithReturnedAt());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        var registerTypeCaptor = ArgumentCaptor.forClass(TimeRegisterType.class);
        doNothing().when(workdayRepository).setTimeRegister(workdayCaptor.capture(), registerTypeCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T17:00:00"));

        assertEquals("2021-01-01T17:00", workdayCaptor.getValue().getEndedAt().toString());
        assertEquals(TimeRegisterType.ENDED_AT, registerTypeCaptor.getValue());
    }

    @Test
    public void shouldIncrementSecondsWorked_whenParamIsAPausedAtRegister() throws InterruptedException {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithStartedAt());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        doNothing().when(workdayRepository).updateWorkedSeconds(workdayCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T12:05:00"));

        // Waiting async task
        Thread.sleep(125);

        assertEquals(14700L, workdayCaptor.getValue().getSecondsWorked());
    }

    @Test
    public void shouldIncrementSecondsWorked_whenParamIsAnEndedAtRegister() throws InterruptedException {
        when(workdayRepository.findByDate(eq(LocalDate.parse("2021-01-01"))))
                .thenReturn(mockWorkdayWithReturnedAt());

        var workdayCaptor = ArgumentCaptor.forClass(Workday.class);
        doNothing().when(workdayRepository).updateWorkedSeconds(workdayCaptor.capture());

        workdayService.registerTime(LocalDateTime.parse("2021-01-01T16:05:00"));

        // Waiting async task
        Thread.sleep(125);

        assertEquals(25500L, workdayCaptor.getValue().getSecondsWorked());
    }

}
