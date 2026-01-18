package com.victor.healthtracker.steps;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entitate pentru pasii zilnici ai utilizatorului
 * O inregistrare pentru o zi specifica care contine data si numarul de pasi facuti in acea zi
 * Este mapata la o tabela in baza de date prin Hibernate (fiecare obiect DailySteps = un rand in tabela DailySteps)
 *
 * -id: Primary Key pentru fiecare inregistrare, generat automat
 * -date: Data calendaristica pentru care se inregistreaza pasii
 * -steps: Numarul de pasi efectuati in acea zi
 * 
 * Metode:
 * -DailySteps(): Constructor gol necesar pentru JPA
 * -DailySteps(date, steps): Constructor cu parametri pentru crearea unui obiect nou (date = data inregistrarii, steps = numarul de pasi)
 * -getId(), setId(): Returneaza/seteaza ID-ul inregistrarii
 * -getDate(), setDate(): Returneaza/seteaza data inregistrarii
 * -getSteps(), setSteps(): Returneaza/seteaza numarul de pasi
 */
@Entity
public class DailySteps {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private int steps;
    public DailySteps() {
    }
    public DailySteps(LocalDate date, int steps) {
        this.date=date;
        this.steps=steps;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id=id;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date=date;
    }
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps=steps;
    }
}