package it.epicode.esame_organizzazione_eventi.prenotazione;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrenotazioneDTO {

    @NotNull(message = "Inserire l'id dell'evento")
    private Long eventoId;

    @Positive(message ="Il numero non può essere negativo!" )
    @NotNull(message = "Inserire posti da prenotare!")
    private int numeroPosti;

}
