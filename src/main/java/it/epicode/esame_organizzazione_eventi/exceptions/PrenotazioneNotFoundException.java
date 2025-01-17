package it.epicode.esame_organizzazione_eventi.exceptions;

public class PrenotazioneNotFoundException extends RuntimeException {
    public PrenotazioneNotFoundException(String message) {
        super(message);
    }
}
