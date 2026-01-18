package com.victor.healthtracker.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pentru lucrul cu tabela Workout din baza de date
 * 
 * -Extinde JpaRepository pentru operatii CRUD de baza (save, findAll, delete etc.)
 * -Spring genereaza automat implementarea acestei interfete
 * -Folosita pentru a salva si citi antrenamente
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Long> {}