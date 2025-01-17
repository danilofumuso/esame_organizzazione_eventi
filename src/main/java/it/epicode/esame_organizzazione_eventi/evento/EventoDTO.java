package it.epicode.esame_organizzazione_eventi.evento;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {

    private String titolo;

    private String descrizione;

    private LocalDate data;

    private String luogo;

    private int postiDisponibili;

}
