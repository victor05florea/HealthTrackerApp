package com.victor.healthtracker.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository pentru lucrul cu tabela DailySteps din baza de date.
 * 
 * Ce face aceasta interfata:
 * - Extinde JpaRepository pentru operatii CRUD de baza (save, findAll, delete, etc.)
 * - Are o metoda speciala findByDate() pentru a gasi pasii pentru o zi specifica
 * - Spring genereaza automat implementarea acestei interfete
 */
@Repository
public interface StepsRepository extends JpaRepository<DailySteps, Long> {
    
    /**
     * Gaseste inregistrarea de pasi pentru o data specifica.
     * 
     * @param date data pentru care se cauta inregistrarea
     * @return Optional care contine DailySteps daca exista, altfel Optional.empty()
     */
    Optional<DailySteps> findByDate(LocalDate date);
}