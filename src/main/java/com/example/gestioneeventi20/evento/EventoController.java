package com.example.gestioneeventi20.evento;

import com.example.gestioneeventi20.exceptions.NotFoundException;
import com.example.gestioneeventi20.utente.Utente;
import com.example.gestioneeventi20.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    private EventoService eventoService;


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_DI_EVENTI', 'UTENTE_NORMALE')")
    public Page<Evento> getEventi(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String orderBy){
        return eventoService.getEventi(page, size, orderBy);
    }


    @GetMapping(value = "/{id}")
    public Evento findById(@PathVariable int id) throws NotFoundException {
        return eventoService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Evento findByIdAndUpdate(@PathVariable int id, @RequestBody Evento body) throws NotFoundException {
        return eventoService.findByIdAndUpdate(id, body);
    }


    @PostMapping("/upload/{id}")
    public String uploadExample(@PathVariable long id, @RequestParam("immagine_profilo") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return eventoService.uploadPicture(id,body);
    }
}
