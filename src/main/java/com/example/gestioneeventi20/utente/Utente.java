package com.example.gestioneeventi20.utente;

import com.example.gestioneeventi20.enums.Role;
import com.example.gestioneeventi20.evento.Evento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="utenti")
@JsonIgnoreProperties({ "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @NotEmpty
    @Column(name="email")
    private String email;
    @NotEmpty
    @Column(name="password")
    private String password;
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "prenotazioni",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<Evento> eventi;

    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
