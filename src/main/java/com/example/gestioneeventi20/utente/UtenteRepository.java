package com.example.gestioneeventi20.utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UtenteRepository  extends JpaRepository<Utente,Long> {
    Optional<Utente> findByEmail(String ics);
    @Query(value = "SELECT * FROM utenti ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Utente findRandomUtente();
    @Transactional
    @Modifying
    @Query(value="DELETE FROM prenotazioni WHERE prenotazioni.utente_id = :utenteId AND prenotazioni.evento_id = :eventoId", nativeQuery = true)
    void deletePrenotazioneByIds(int utenteId, int eventoId);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM prenotazioni WHERE prenotazioni.id = :id", nativeQuery = true)
    void deletePrenotazioneById(int id);
}
