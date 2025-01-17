package it.epicode.esame_organizzazione_eventi.evento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {

    @NotBlank(message = "Il campo titolo non può essere vuoto")
    private String titolo;

    private String descrizione;

    @FutureOrPresent(message = "La data non può essere passata!")
    private LocalDate data;

    @NotBlank(message = "Il campo luogo non può essere vuoto")
    private String luogo;

    @NotNull(message = "Inserire posti disponibili per l'evento")
    @Positive(message ="Il numero non può essere negativo!" )
    private int postiDisponibili;

}
