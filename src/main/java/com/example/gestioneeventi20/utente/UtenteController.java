package com.example.gestioneeventi20.utente;

import com.example.gestioneeventi20.evento.Evento;
import com.example.gestioneeventi20.evento.EventoService;
import com.example.gestioneeventi20.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private EventoService eventoService;


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_DI_EVENTI', 'UTENTE_NORMALE')")
    public Page<Utente> getUser(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String orderBy){
        return utenteService.getUtenti(page, size, orderBy);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    };

    @PutMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal Utente currentUser, @RequestBody Utente body){
        return utenteService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void getProfile(@AuthenticationPrincipal Utente currentUser){
        utenteService.findByIdAndDelete(currentUser.getId());
    };
    @GetMapping(value = "/{id}")
    public Utente findById(@PathVariable int id)  {
        return utenteService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Utente findByIdAndUpdate(@PathVariable int id, @RequestBody Utente body) throws NotFoundException {
        return utenteService.findByIdAndUpdate(id, body);
    }
    @PatchMapping("/{eventId}/{id}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public long BuyReservation(@PathVariable int id, @PathVariable int eventId) throws NotFoundException {
    eventoService.findById(eventId);
    if(eventoService.findById(eventId)!=null && eventoService.findById(eventId).getData().isAfter(LocalDate.now().minusDays(1))){
        Evento evento = eventoService.findById(eventId);
        Utente users = utenteService.findById(id);
        if(users!=null){
            users.getEventi().add(evento);
            utenteService.findByIdAndUpdate(users.getId(),users);
        }
    }
    return id;
    }

        @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        utenteService.findByIdAndDelete(id);
    }



}