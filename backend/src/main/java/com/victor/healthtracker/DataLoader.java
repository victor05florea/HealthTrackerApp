package com.victor.healthtracker;

import com.victor.healthtracker.sleep.SleepRepository;
import com.victor.healthtracker.sleep.SleepSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Clasa care populeaza baza de date cu date de test la pornirea aplicatiei
 * Ruleaza automat cand porneste serverul (CommandLineRunner), verifica daca baza de date e goala si daca e goala, adauga cateva sesiuni de somn de test
 * Utila pentru dezvoltare ca sa nu ai tabele goale
 * 
 * Campuri:
 * -sleepRepository: Repository-ul pentru sesiunile de somn, injectat prin constructor
 * 
 * Metode:
 * -DataLoader(sleepRepository): Constructor pentru injectarea dependentei SleepRepository (sleepRepository = repository-ul pentru sesiunile de somn)
 * -run(args): Metoda care ruleaza o singura data la pornirea aplicatiei, populeaza baza de date cu date de test daca aceasta este goala (args = argumentele liniei de comanda)
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final SleepRepository sleepRepository;

    public DataLoader(SleepRepository sleepRepository) {
        this.sleepRepository=sleepRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (sleepRepository.count()==0) {
            System.out.println("Se adauga date de test pentru Somn:");

            LocalDateTime culcare1=LocalDateTime.now().minusDays(1).withHour(23).withMinute(0);
            LocalDateTime trezire1=LocalDateTime.now().minusDays(1).withHour(7).withMinute(0);
            sleepRepository.save(new SleepSession(culcare1, trezire1));

            LocalDateTime culcare2=LocalDateTime.now().minusDays(2).withHour(0).withMinute(30);
            LocalDateTime trezire2=LocalDateTime.now().minusDays(2).withHour(6).withMinute(30);
            sleepRepository.save(new SleepSession(culcare2, trezire2));
            System.out.println("Datele au fost salvate in baza de date");
        }
    }
}