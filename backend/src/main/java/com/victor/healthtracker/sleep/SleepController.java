package com.victor.healthtracker.sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Controller pentru gestionarea sesiunilor de somn
 * Returneaza toate sesiunile de somn salvate, permite adaugarea unei sesiuni de somn noi si are un endpoint pentru popularea bazei cu date de test
 * Comunică cu baza de date prin SleepRepository
 * 
 * Campuri:
 * -sleepRepository: Repository-ul pentru gestionarea sesiunilor de somn
 * 
 * Metode:
 * -getAllSleepSessions(): Endpoint GET care returneaza toate sesiunile de somn inregistrate (returneaza o lista de obiecte SleepSession)
 * -addSleepSession(session): Endpoint POST care adauga o sesiune de somn noua in baza de date (session = sesiunea de somn de salvat, returneaza sesiunea salvata)
 * -addFakeData(): Endpoint GET pentru popularea bazei de date cu date de test (returneaza mesaj de confirmare)
 */
@RestController
@RequestMapping("/api/sleep")
@CrossOrigin("*")
public class SleepController {

    @Autowired
    private SleepRepository sleepRepository;

    @GetMapping
    public List<SleepSession> getAllSleepSessions() {
        return sleepRepository.findAll();
    }

    @PostMapping
    public SleepSession addSleepSession(@RequestBody SleepSession session) {
        return sleepRepository.save(session);
    }

    @GetMapping("/populate")
    public String addFakeData() {
        SleepSession s1=new SleepSession(LocalDateTime.now().minusHours(8), LocalDateTime.now());
        SleepSession s2=new SleepSession(LocalDateTime.now().minusDays(1).minusHours(9), LocalDateTime.now().minusDays(1).minusHours(1));
        sleepRepository.save(s1);
        sleepRepository.save(s2);
        return "Am adaugat 2 sesiuni de somn in baza de date";
    }
}