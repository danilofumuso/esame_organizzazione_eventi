package it.epicode.esame_organizzazione_eventi.exceptions;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(String message) {
        super(message);
    }
}
