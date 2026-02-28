package com.victor.healthtracker.sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller pentru gestionarea sesiunilor de somn
 * Returneaza istoricul sesiunilor de somn, permite adaugarea de sesiuni noi si stergerea sesiunilor existente
 * Comunica cu baza de date prin SleepRepository
 * -sleepRepository: Repository-ul pentru gestionarea sesiunilor de somn in baza de date
 * 
 * Metode:
 * -getHistory(): Endpoint GET care returneaza istoricul sesiunilor de somn ordonate descrescator dupa data (returneaza lista cu cele mai recente sesiuni primele)
 * -addSession(session): Endpoint POST care adauga o sesiune de somn noua (session este sesiunea de salvat, valideaza ca ora de trezire sa fie dupa ora de culcare, returneaza sesiunea salvata)
 * -deleteSession(id): Endpoint DELETE care sterge o sesiune de somn dupa ID (id este ID-ul sesiunii de sters)
 */
@RestController
@RequestMapping("/api/sleep")
@CrossOrigin("*")
public class SleepController {
    @Autowired
    private SleepRepository sleepRepository;

    @GetMapping("/history")
    public List<SleepSession> getHistory() {
        return sleepRepository.findAllByOrderByStartTimeDesc();
    }

    @PostMapping
    public SleepSession addSession(@RequestBody SleepSession session) {
        if (session.getEndTime().isBefore(session.getStartTime())) {
            throw new RuntimeException("Ora de trezire nu poate fi inaintea orei de culcare!");
        }
        return sleepRepository.save(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sleepRepository.deleteById(id);
    }
}