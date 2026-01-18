package com.victor.healthtracker.sleep;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entitate pentru o sesiune de somn a utilizatorului
 * O sesiune de somn reprezinta o noapte de dormit si este mapata la o tabela in baza de date prin Hibernate
 *
 * -id: Identificatorul unic al sesiunii de somn, generat automat de baza de date
 * -startTime: Momentul in care utilizatorul s-a culcat (ora de adormire)
 * -endTime: Momentul in care utilizatorul s-a trezit (ora de trezire)
 * 
 * Metode:
 * -SleepSession(): Constructor gol necesar pentru JPA
 * -SleepSession(startTime, endTime): Constructor cu parametri pentru crearea unei noi sesiuni (startTime reprezinta data si ora culcarii, endTime = data si ora trezirii)
 * -getDurationHours(): Calculeaza durata somnului in ore (returneaza numarul de ore rotunjit la o zecimala, apare automat in JSON ca "durationHours")
 * -getId(), setId(): Returneaza/seteaza ID-ul sesiunii de somn
 * -getStartTime(), setStartTime(): Returneaza/seteaza momentul cand utilizatorul a adormit
 * -getEndTime(), setEndTime(): Returneaza/seteaza momentul cand utilizatorul s-a trezit
 */
@Entity
public class SleepSession {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public SleepSession(){}
    public SleepSession(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime=startTime;
        this.endTime=endTime;
    }
    public double getDurationHours() {
        if (startTime!=null && endTime!=null) {
            long minutes=ChronoUnit.MINUTES.between(startTime,endTime);
            return Math.round((minutes/60.0)*10.0)/10.0;
        }
        return 0;
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