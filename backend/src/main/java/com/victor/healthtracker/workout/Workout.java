package com.victor.healthtracker.workout;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitate pentru un antrenament
 * Un antrenament reprezinta o sesiune de antrenament (ex: "Piept", "Cardio") care contine tipul antrenamentului si data cand a avut loc
 * Este tabela "parinte" - un antrenament are mai multe exercitii (relatie One-to-Many cu Exercise)
 * Cand sterg un antrenament, se sterg automat si exercitiile lui (cascade)
 *
 * -id: ID unic pentru antrenament, generat automat de baza de date
 * -type: Tipul sau numele antrenamentului (ex: "Spate + Biceps", "Cardio", "Leg Day")
 * -date: Data si ora la care a avut loc antrenamentul
 * -exercises: Lista de exercitii asociate acestui antrenament (relatie One-to-Many, mappedBy="workout", cascade=CascadeType.ALL, orphanRemoval=true)
 * 
 * Metode:
 * -Workout(): Constructor gol necesar pentru JPA
 * -Workout(type, date): Constructor pentru crearea unui nou antrenament (type este tipul antrenamentului, date este data antrenamentului)
 * -getId()/setId(id): Returneaza/seteaza ID-ul antrenamentului
 * -getType()/setType(type): Returneaza/seteaza tipul antrenamentului
 * -getDate()/setDate(date): Returneaza/seteaza data si ora antrenamentului
 * -getExercises()/etExercises(exercises): Returneaza/seteaza lista completa de exercitii
 */
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDateTime date;

    @OneToMany(mappedBy="workout",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Exercise> exercises=new ArrayList<>();
    public Workout() {
    }
    public Workout(String type, LocalDateTime date) {
        this.type=type;
        this.date=date;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id=id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type=type;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date=date;
    }
    public List<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises=exercises;
    }
}