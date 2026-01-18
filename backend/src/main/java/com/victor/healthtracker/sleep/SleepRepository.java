package com.victor.healthtracker.sleep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pentru lucrul cu tabela SleepSession din baza de date.
 *
 * -Extinde JpaRepository pentru operatii CRUD de baza (save, findAll, delete, etc.)
 * -Spring genereaza automat implementarea acestei interfete
 * -Folosita pentru a salva si citi sesiuni de somn
 */
@Repository
public interface SleepRepository extends JpaRepository<SleepSession, Long>{}