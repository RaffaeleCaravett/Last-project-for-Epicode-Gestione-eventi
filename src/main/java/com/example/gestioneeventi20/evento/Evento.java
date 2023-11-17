package com.example.gestioneeventi20.evento;

import com.example.gestioneeventi20.utente.Utente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="eventi")
@JsonIgnoreProperties({ "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @NotEmpty
    @Column(name="titolo")
    private String titolo;
    @NotEmpty
    @Column(name="descrizione")
    private String descrizione;
    @NotEmpty
    @Column(name="luogo")
    private String luogo;
    @NotNull
    @Column(name="data")
    private LocalDate data;
    @NotNull
    @Column(name="numero_massimo_partecipanti")
    private int numeroMassimoPartecipanti;
    @Column(name="img")
    private String img;
    @ManyToMany(mappedBy = "eventi")
    private List<Utente> utenti;


    public Evento(String titolo, String descrizione, String luogo, LocalDate data, int numeroMassimoPartecipanti) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.luogo = luogo;
        this.data = data;
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
    }
}
