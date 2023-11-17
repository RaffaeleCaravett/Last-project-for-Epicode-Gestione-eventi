package com.example.gestioneeventi20.utente;

import com.cloudinary.Cloudinary;
import com.example.gestioneeventi20.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;


    // @Autowired
    //private EmailSender emailSender;

    /*public Dipendente save(DipendenteDTO body) throws IOException {
        System.out.println(body.nome());
        dipendenteRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestExceptions("L'email " + body.email() + " è già in uso!");
        });
        Dipendente dipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), body.password());
        return dipendenteRepository.save(dipendente);
    }*/

    public Page<Utente> getUtenti(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return utenteRepository.findAll(pageable);
    }

    public Utente findById(long id) throws NotFoundException {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente findByIdAndUpdate(long id, Utente body) throws NotFoundException {
        Utente found = this.findById(id);
        found.setEmail(body.getEmail());
        if(body.getEventi()!=null){
            found.setEventi(body.getEventi());
        }
        return utenteRepository.save(found);
    }

    public Utente getRandomUtente() throws NotFoundException {
        return utenteRepository.findRandomUtente();
    }

    public Utente findByEmail(String email) throws Exception {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Utente con email "+ email + " non trovato"));
    }

    public long deleteAllReservations(int id,int eventId) {
        utenteRepository.deletePrenotazioneByIds(id,eventId);
        return id;
    }

    public long deletePrenotazioneById(int id) {
        utenteRepository.deletePrenotazioneById(id);
        return id;
    }
}
