package it.epicode.esame_organizzazione_eventi.exceptions;

public class OrganizzatoreNotFoundException extends RuntimeException {
    public OrganizzatoreNotFoundException(String message) {
        super(message);
    }
}
