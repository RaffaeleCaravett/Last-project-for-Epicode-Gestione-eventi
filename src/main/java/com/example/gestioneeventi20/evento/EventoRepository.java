package com.example.gestioneeventi20.evento;

import com.example.gestioneeventi20.utente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Long> {
    @Query(value = "SELECT * FROM eventi ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Evento findRandomEvento();
}
