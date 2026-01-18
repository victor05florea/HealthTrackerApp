package com.victor.healthtracker.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Controller pentru gestionarea pasilor utilizatorului
 * Permite citirea numarului de pasi pentru ziua de azi si salvarea/actualizarea pasilor pentru ziua curenta
 * Comunică cu baza de date prin StepsRepository si returneaza date in format JSON pentru aplicatia mobila
 * 
 * Campuri:
 * -stepsRepository: Repository-ul pentru lucrul cu datele despre pasi din baza de date
 * 
 * Metode:
 * -getTodaySteps(): Endpoint GET care returneaza numarul de pasi pentru ziua de azi (returneaza obiectul DailySteps pentru ziua curenta sau un obiect nou cu 0 pasi daca nu exista date)
 * -updateSteps(stepsCount): Endpoint POST care salveaza sau actualizeaza numarul de pasi pentru ziua curenta (stepsCount = numarul de pasi primit din request, returneaza obiectul DailySteps salvat in baza de date)
 */
@RestController
@RequestMapping("/api/steps")
@CrossOrigin("*")
public class StepsController {

    @Autowired
    private StepsRepository stepsRepository;

    @GetMapping("/today")
    public DailySteps getTodaySteps() {
        LocalDate today = LocalDate.now();

        return stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));
    }

    @PostMapping
    public DailySteps updateSteps(@RequestBody int stepsCount) {
        LocalDate today = LocalDate.now();
        DailySteps dailySteps = stepsRepository.findByDate(today)
                .orElse(new DailySteps(today, 0));
        dailySteps.setSteps(stepsCount);
        return stepsRepository.save(dailySteps);
    }
}