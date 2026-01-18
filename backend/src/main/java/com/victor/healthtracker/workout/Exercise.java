package com.victor.healthtracker.workout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Entitate pentru un exercitiu dintr-un antrenament
 * Un exercitiu reprezinta un exercitiu specific dintr-un antrenament care contine numele exercitiului, serii, repetari si greutate
 * Este tabela "copil" - apartine unui antrenament (Workout) prin relatie Many-to-One (multe Exercise -> un Workout)
 * Exemplu: Antrenament "Piept" contine exercitiile "Impins la piept", "Flotari", etc.
 * 
 * Campuri:
 * -id: ID-ul unic al exercitiului, generat automat de baza de date
 * -name: Numele exercitiului (ex: "Impins la piept")
 * -sets: Numarul de serii efectuate
 * -reps: Numarul de repetari pe serie
 * -weight: Greutatea folosita (ex: 12.5 kg)
 * -workout: Relatia cu antrenamentul parinte (ManyToOne, Foreign Key 'workout_id', @JsonIgnore previne serializarea circulara)
 * 
 * Metode:
 * -Exercise(): Constructor gol necesar pentru JPA
 * -Exercise(name, sets, reps, weight, workout): Constructor cu date pentru crearea unui exercitiu nou (name = numele exercitiului, sets = numarul de serii, reps = numarul de repetari, weight = greutatea folosita, workout = antrenamentul din care face parte)
 * -getId(): Returneaza ID-ul exercitiului
 * -setId(id): Seteaza ID-ul exercitiului
 * -getName(): Returneaza numele exercitiului
 * -setName(name): Seteaza numele exercitiului
 * -getSets(): Returneaza numarul de serii
 * -setSets(sets): Seteaza numarul de serii
 * -getReps(): Returneaza numarul de repetari
 * -setReps(reps): Seteaza numarul de repetari
 * -getWeight(): Returneaza greutatea folosita
 * -setWeight(weight): Seteaza greutatea folosita
 * -getWorkout(): Returneaza antrenamentul din care face parte exercitiul
 * -setWorkout(workout): Seteaza antrenamentul din care face parte exercitiul
 */
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private double weight;

    @ManyToOne
    @JoinColumn(name="workout_id")
    @JsonIgnore
    private Workout workout;

    public Exercise() {}

    public Exercise(String name,int sets,int reps,double weight,Workout workout) {
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.weight=weight;
        this.workout=workout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets=sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps=reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight=weight;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout=workout;
    }
}