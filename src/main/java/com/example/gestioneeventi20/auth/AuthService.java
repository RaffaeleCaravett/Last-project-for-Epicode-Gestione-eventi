package com.example.gestioneeventi20.auth;

import com.example.gestioneeventi20.enums.Role;
import com.example.gestioneeventi20.exceptions.BadRequestException;
import com.example.gestioneeventi20.exceptions.UnauthorizedException;
import com.example.gestioneeventi20.security.JWTTools;
import com.example.gestioneeventi20.utente.Utente;
import com.example.gestioneeventi20.utente.UtenteRepository;
import com.example.gestioneeventi20.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UtenteService usersService;

    @Autowired
    private UtenteRepository dipendenteRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UtenteLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        Utente user = usersService.findByEmail(body.email());
    // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.password(), user.getPassword()))  {
        // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
        return jwtTools.createToken(user);
    } else {
        // 4. Se le credenziali NON sono OK --> 401
        throw new UnauthorizedException("Credenziali non valide!");
    }
    }

    public Utente registerUser(Utente body) throws IOException {

        // verifico se l'email è già utilizzata
        dipendenteRepository.findByEmail(body.getEmail()).ifPresent( user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });
        Utente newUser = new Utente();
        newUser.setPassword(bcrypt.encode(body.getPassword())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.getEmail());
        newUser.setRole(Role.UTENTE_NORMALE);
        return dipendenteRepository.save(newUser);
    }

}