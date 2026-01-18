package com.victor.healthtracker.sleep;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clasa de teste pentru SleepController
 * Testeaza endpoint-urile REST ale controller-ului pentru gestionarea sesiunilor de somn
 * Foloseste MockMvc pentru simularea cererilor HTTP si MockBean pentru repository
 * 
 * Campuri:
 * -mockMvc: Simuleaza cereri HTTP pentru testarea endpoint-urilor
 * -sleepRepository: Repository mock pentru simularea operatiilor cu baza de date
 * -objectMapper: Converteste obiecte Java in JSON si invers pentru testare
 * 
 * Metode de test:
 * -shouldReturnHistoryOrderedByStartTimeDesc(): Testeaza GET /api/sleep/history - verifica ca sesiunile sunt returnate ordonate descrescator dupa data
 * -shouldSaveValidSleepSession(): Testeaza POST /api/sleep - verifica salvarea unei sesiuni valide de somn
 * -shouldRejectSleepSessionWithEndTimeBeforeStartTime(): Testeaza validarea - verifica ca sesiunile cu ora de trezire inaintea orei de culcare sunt respinse
 * -shouldDeleteSleepSession(): Testeaza DELETE /api/sleep/{id} - verifica stergerea unei sesiuni dupa ID
 * -shouldReturnEmptyListWhenNoSleepSessions(): Testeaza GET /api/sleep/history - verifica ca returneaza lista goala cand nu exista sesiuni
 */
@WebMvcTest(SleepController.class)
public class SleepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SleepRepository sleepRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testeaza ca endpoint-ul GET /api/sleep/history returneaza sesiunile ordonate descrescator dupa data de inceput
     */
    @Test
    public void shouldReturnHistoryOrderedByStartTimeDesc() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        SleepSession session1 = new SleepSession(now.minusDays(2), now.minusDays(2).plusHours(8));
        SleepSession session2 = new SleepSession(now.minusDays(1), now.minusDays(1).plusHours(7));
        session1.setId(1L);
        session2.setId(2L);
        
        List<SleepSession> sessions = Arrays.asList(session2, session1);
        when(sleepRepository.findAllByOrderByStartTimeDesc()).thenReturn(sessions);

        mockMvc.perform(get("/api/sleep/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[1].id").value(1));
    }

    /**
     * Testeaza ca endpoint-ul POST /api/sleep salveaza corect o sesiune valida de somn
     */
    @Test
    public void shouldSaveValidSleepSession() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(8);
        SleepSession newSession = new SleepSession(startTime, endTime);
        SleepSession savedSession = new SleepSession(startTime, endTime);
        savedSession.setId(1L);
        
        when(sleepRepository.save(any(SleepSession.class))).thenReturn(savedSession);

        mockMvc.perform(post("/api/sleep")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSession)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(sleepRepository, times(1)).save(any(SleepSession.class));
    }

    /**
     * Testeaza ca endpoint-ul POST /api/sleep respinge sesiunile invalide unde ora de trezire este inaintea orei de culcare
     */
    @Test
    public void shouldRejectSleepSessionWithEndTimeBeforeStartTime() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(1);
        SleepSession invalidSession = new SleepSession(startTime, endTime);

        mockMvc.perform(post("/api/sleep")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSession)))
                .andExpect(status().is5xxServerError());
        
        verify(sleepRepository, never()).save(any(SleepSession.class));
    }

    /**
     * Testeaza ca endpoint-ul DELETE /api/sleep/{id} sterge corect o sesiune dupa ID
     */
    @Test
    public void shouldDeleteSleepSession() throws Exception {
        Long sessionId = 1L;
        doNothing().when(sleepRepository).deleteById(sessionId);

        mockMvc.perform(delete("/api/sleep/{id}", sessionId))
                .andExpect(status().isOk());
        
        verify(sleepRepository, times(1)).deleteById(sessionId);
    }

    /**
     * Testeaza ca endpoint-ul GET /api/sleep/history returneaza lista goala cand nu exista sesiuni in baza de date
     */
    @Test
    public void shouldReturnEmptyListWhenNoSleepSessions() throws Exception {
        when(sleepRepository.findAllByOrderByStartTimeDesc()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/sleep/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}