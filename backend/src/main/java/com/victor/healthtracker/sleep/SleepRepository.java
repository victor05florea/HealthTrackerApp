package com.victor.healthtracker.sleep;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pentru lucrul cu tabela SleepSession din baza de date
 * Extinde JpaRepository pentru operatii CRUD de baza (save, findAll, delete etc)
 * Spring genereaza automat implementarea acestei interfete
 * 
 * Metode:
 * -findAllByOrderByStartTimeDesc(): Gaseste toate sesiunile de somn ordonate descrescator dupa data de inceput (cele mai recente primele, util pentru afisarea istoricului)
 */
public interface SleepRepository extends JpaRepository<SleepSession, Long> {
    List<SleepSession> findAllByOrderByStartTimeDesc();
}