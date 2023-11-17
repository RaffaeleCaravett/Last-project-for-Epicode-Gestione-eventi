package com.example.gestioneeventi20.evento;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.gestioneeventi20.exceptions.BadRequestException;
import com.example.gestioneeventi20.exceptions.NotFoundException;
import com.example.gestioneeventi20.utente.Utente;
import com.example.gestioneeventi20.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;


    // @Autowired
    //private EmailSender emailSender;
    @Autowired
    private Cloudinary cloudinary;

    public Evento save(Evento body) throws IOException {
        if(body.getUtenti().size()< body.getNumeroMassimoPartecipanti()){
            return eventoRepository.save(body);
        }else{
            throw new BadRequestException("L'evento ha già raggiunto un numero massimo di partecipanti");
        }

    }

    public Page<Evento> getEventi(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return eventoRepository.findAll(pageable);
    }

    public Evento findById(long id) throws NotFoundException {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Evento found = this.findById(id);
        eventoRepository.delete(found);
    }

    public Evento findByIdAndUpdate(long id, Evento body) throws NotFoundException {
        Evento found = this.findById(id);
        found.setData(body.getData());
        found.setDescrizione(body.getDescrizione());
        found.setLuogo(body.getLuogo());
        found.setTitolo(body.getTitolo());
        found.setNumeroMassimoPartecipanti(body.getNumeroMassimoPartecipanti());
        return eventoRepository.save(found);
    }

    public Evento getRandomUtente() throws NotFoundException {
        return eventoRepository.findRandomEvento();
    }

    public String uploadPicture(long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Evento evento = eventoRepository.findById(id).orElse(null);
            if (evento != null) {
                evento.setImg(imageUrl);
                eventoRepository.save(evento);
            }

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
    }

}
