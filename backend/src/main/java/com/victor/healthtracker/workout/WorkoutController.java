package com.victor.healthtracker.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller pentru gestionarea antrenamentelor si exercitiilor
 * Returneaza toate antrenamentele din baza de date, permite crearea unui antrenament nou, stergerea unui antrenament dupa ID si adaugarea de exercitii la un antrenament existent
 * Comunica cu baza de date prin WorkoutRepository
 * -workoutRepository: Repository-ul pentru gestionarea antrenamentelor in baza de date
 * 
 * Metode:
 * -getAllWorkouts(): Endpoint GET care returneaza toate antrenamentele din baza de date (returneaza o lista JSON cu toate obiectele Workout)
 * -addWorkout(workout): Endpoint POST care creeaza un antrenament nou (workout = obiectul antrenamentului de salvat, daca data nu este specificata se seteaza automat la data si ora curenta, returneaza antrenamentul salvat)
 * -deleteWorkout(id): Endpoint DELETE care sterge un antrenament dupa ID (id = ID-ul antrenamentului de sters, datorita CascadeType.ALL stergerea antrenamentului va sterge automat si toate exercitiile asociate lui)
 * -addExerciseToWorkout(id, exercise): Endpoint POST care adauga un exercitiu specific unui antrenament existent (id = ID-ul antrenamentului parinte, exercise = datele exercitiului copil, face legatura intre cele doua tabele Workout si Exercise, returneaza exercitiul salvat)
 */
@RestController
@RequestMapping("/api/workouts")
@CrossOrigin("*")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @PostMapping
    public Workout addWorkout(@RequestBody Workout workout) {
        if (workout.getDate()==null) {
            workout.setDate(LocalDateTime.now());
        }
        return workoutRepository.save(workout);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable Long id) {
        workoutRepository.deleteById(id);
    }

    @PostMapping("/{id}/exercises")
    public Exercise addExerciseToWorkout(@PathVariable Long id, @RequestBody Exercise exercise) {
        Workout workout=workoutRepository.findById(id).orElseThrow();
        exercise.setWorkout(workout);
        workout.getExercises().add(exercise);
        workoutRepository.save(workout);

        return exercise;
    }
}