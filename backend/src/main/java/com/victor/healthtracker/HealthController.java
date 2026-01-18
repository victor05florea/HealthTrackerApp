package com.victor.healthtracker;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller simplu pentru verificarea serverului
 * Verifica daca serverul a pornit corect, returneaza un mesaj simplu cand accesezi /test
 * Nu foloseste baza de date, doar testeaza conexiunea
 * 
 * Metode:
 * -sayHello(): Endpoint GET de test pentru verificarea functionalitatii serverului
 */
@RestController
@CrossOrigin("*")
public class HealthController {
    @GetMapping("/test")
    public String sayHello() {
        return "Salut! Backend-ul Java functioneaza!";
    }
}