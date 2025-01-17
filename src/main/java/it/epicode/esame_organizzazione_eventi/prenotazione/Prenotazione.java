package it.epicode.esame_organizzazione_eventi.prenotazione;

import it.epicode.esame_organizzazione_eventi.auth.AppUser;
import it.epicode.esame_organizzazione_eventi.evento.Evento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "data_prenotazione")
    private LocalDate dataPrenotazione;

    private int numeroPosti;
}
