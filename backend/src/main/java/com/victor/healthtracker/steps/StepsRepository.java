package com.victor.healthtracker.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository pentru lucrul cu tabela DailySteps din baza de date.
 *
 * -Extinde JpaRepository pentru operatii CRUD de baza (save, findAll, delete etc)
 * -Are o metoda findByDate() pentru a gasi pasii pentru o zi specifica
 * -Spring genereaza automat implementarea acestei interfete
 */
@Repository
public interface StepsRepository extends JpaRepository<DailySteps, Long> {
    Optional<DailySteps> findByDate(LocalDate date);
}