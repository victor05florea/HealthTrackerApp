package com.victor.healthtracker.sleep;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clasa de teste pentru entitatea SleepSession
 * Testeaza functionalitatea entitatii SleepSession inclusiv constructori, getteri, setteri si calculul duratei somnului
 * 
 * Metode de test:
 * -testSleepSessionEntity(): Testeaza crearea unei sesiuni cu constructorul cu parametri si verificarea corectitudinii datelor
 * -testEmptyConstructor(): Testeaza constructorul gol si verifica ca campurile sunt null initial
 * -testModifySessionTimes(): Testeaza modificarea timpurilor unei sesiuni prin setteri
 * -testGetDurationHoursFor8HoursSleep(): Testeaza calculul duratei pentru un somn de 8 ore
 * -testGetDurationHoursFor7AndHalfHoursSleep(): Testeaza calculul duratei pentru un somn de 7.5 ore
 * -testGetDurationHoursReturnsZeroWhenTimesAreNull(): Testeaza ca durata returneaza 0 cand timpurile sunt null
 * -testGetDurationHoursForShortSleep(): Testeaza calculul duratei pentru un somn scurt (2.5 ore)
 * -testGetIdAndSetId(): Testeaza getter si setter pentru ID
 * -testSetEndTime(): Testeaza setter pentru ora de trezire
 * -testGetDurationHoursRoundsToOneDecimal(): Testeaza ca durata este rotunjita corect la o zecimala
 */
public class SleepSessionTest {

    /**
     * Testeaza crearea unei sesiuni de somn cu constructorul cu parametri si verificarea ca datele sunt stocate corect
     */
    @Test
    public void testSleepSessionEntity() {
        LocalDateTime culcare = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime trezire = LocalDateTime.of(2025, 12, 19, 7, 0);

        SleepSession session = new SleepSession(culcare, trezire);

        assertEquals(culcare, session.getStartTime());
        assertEquals(trezire, session.getEndTime());
    }

    /**
     * Testeaza constructorul gol si verifica ca obiectul este creat si campurile sunt null initial
     */
    @Test
    public void testEmptyConstructor() {
        SleepSession session = new SleepSession();
        assertNotNull(session);
        assertNull(session.getStartTime());
        assertNull(session.getEndTime());
    }

    /**
     * Testeaza modificarea timpurilor unei sesiuni prin setteri si verifica ca modificarea este efectuata corect
     */
    @Test
    public void testModifySessionTimes() {
        SleepSession session = new SleepSession();

        LocalDateTime startInitial = LocalDateTime.now();
        session.setStartTime(startInitial);
        assertEquals(startInitial, session.getStartTime());

        LocalDateTime startNou = startInitial.minusHours(1);
        session.setStartTime(startNou);

        assertNotEquals(startInitial, session.getStartTime());
        assertEquals(startNou, session.getStartTime());
    }

    /**
     * Testeaza calculul duratei somnului pentru un somn de exact 8 ore
     */
    @Test
    public void testGetDurationHoursFor8HoursSleep() {
        LocalDateTime startTime = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 12, 19, 7, 0);
        SleepSession session = new SleepSession(startTime, endTime);

        double duration = session.getDurationHours();
        assertEquals(8.0, duration, 0.1);
    }

    /**
     * Testeaza calculul duratei somnului pentru un somn de 7.5 ore (7 ore si 30 minute)
     */
    @Test
    public void testGetDurationHoursFor7AndHalfHoursSleep() {
        LocalDateTime startTime = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 12, 19, 6, 30);
        SleepSession session = new SleepSession(startTime, endTime);

        double duration = session.getDurationHours();
        assertEquals(7.5, duration, 0.1);
    }

    /**
     * Testeaza ca metoda getDurationHours() returneaza 0 cand startTime sau endTime sunt null
     */
    @Test
    public void testGetDurationHoursReturnsZeroWhenTimesAreNull() {
        SleepSession session = new SleepSession();
        double duration = session.getDurationHours();
        assertEquals(0.0, duration);
    }

    /**
     * Testeaza calculul duratei somnului pentru un somn scurt de 2.5 ore
     */
    @Test
    public void testGetDurationHoursForShortSleep() {
        LocalDateTime startTime = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 12, 19, 1, 30);
        SleepSession session = new SleepSession(startTime, endTime);

        double duration = session.getDurationHours();
        assertEquals(2.5, duration, 0.1);
    }

    /**
     * Testeaza getter si setter pentru ID-ul sesiunii de somn
     */
    @Test
    public void testGetIdAndSetId() {
        SleepSession session = new SleepSession();
        assertNull(session.getId());

        Long testId = 123L;
        session.setId(testId);
        assertEquals(testId, session.getId());
    }

    /**
     * Testeaza setter pentru ora de trezire (endTime)
     */
    @Test
    public void testSetEndTime() {
        SleepSession session = new SleepSession();
        LocalDateTime endTime = LocalDateTime.now();
        
        session.setEndTime(endTime);
        assertEquals(endTime, session.getEndTime());
    }

    /**
     * Testeaza ca durata somnului este rotunjita corect la o zecimala (ex: 8.3 ore pentru 8 ore si 17 minute)
     */
    @Test
    public void testGetDurationHoursRoundsToOneDecimal() {
        LocalDateTime startTime = LocalDateTime.of(2025, 12, 18, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 12, 19, 7, 17);
        SleepSession session = new SleepSession(startTime, endTime);

        double duration = session.getDurationHours();
        assertEquals(8.3, duration, 0.1);
    }
}