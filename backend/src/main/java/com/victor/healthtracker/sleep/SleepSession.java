package com.victor.healthtracker.sleep;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entitate pentru o sesiune de somn a utilizatorului
 * O sesiune de somn o reprezinta o noapte de dormit si este mapata la o tabela in baza de date prin Hibernate
 * 
 * Campuri:
 * -id: Identificatorul unic al sesiunii de somn, generat automat de baza de date
 * -startTime: Momentul in care utilizatorul s-a culcat (ora de adormire)
 * -endTime: Momentul in care utilizatorul s-a trezit (ora de trezire)
 * 
 * Metode:
 * -SleepSession(): Constructor implicit necesar pentru JPA
 * -SleepSession(startTime, endTime): Constructor complet pentru crearea unei noi sesiuni (startTime = data si ora culcarii, endTime = data si ora trezirii)
 * -getId(): Returneaza ID-ul sesiunii de somn
 * -setId(id): Seteaza ID-ul sesiunii de somn
 * -getStartTime(): Returneaza momentul cand utilizatorul a adormit (data si ora culcarii)
 * -setStartTime(startTime): Seteaza momentul cand utilizatorul a adormit
 * -getEndTime(): Returneaza momentul cand utilizatorul s-a trezit (data si ora trezirii)
 * -setEndTime(endTime): Seteaza momentul cand utilizatorul s-a trezit
 */
@Entity
public class SleepSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public SleepSession() {
    }

    public SleepSession(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime=startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime=endTime;
    }
}