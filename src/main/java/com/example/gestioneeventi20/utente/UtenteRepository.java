package com.example.gestioneeventi20.utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository  extends JpaRepository<Utente,Long> {
    Optional<Utente> findByEmail(String ics);
    @Query(value = "SELECT * FROM utenti ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Utente findRandomUtente();
}
