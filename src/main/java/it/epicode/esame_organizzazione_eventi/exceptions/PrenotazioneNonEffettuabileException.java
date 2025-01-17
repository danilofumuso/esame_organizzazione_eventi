package it.epicode.esame_organizzazione_eventi.exceptions;

public class PrenotazioneNonEffettuabileException extends RuntimeException{
    public PrenotazioneNonEffettuabileException(String message) {
        super(message);
    }
}
