package it.epicode.esame_organizzazione_eventi.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionsHandlerClass extends ResponseEntityExceptionHandler {

        @ExceptionHandler(value = UserNotFoundException.class)
        protected ResponseEntity<Object> userNotFound(UserNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(value = EventoNotFoundException.class)
        protected ResponseEntity<Object> eventoNotFound(EventoNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(value = PrenotazioneNotFoundException.class)
        protected ResponseEntity<Object> prenotazioneNotFound(PrenotazioneNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(value = PrenotazioneNonEffettuabileException.class)
        protected ResponseEntity<Object> prenotazioneNonEffettuabile(PrenotazioneNonEffettuabileException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(value = AccessDeniedException.class)
        protected ResponseEntity<Object> accessDenied(AccessDeniedException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }

     //da usare per la validazione, notation sugli attributi delle entities tipo @NotBlank,
    //e usare nel service notation @Validated e @Valid per ogni metodo che vuole validazione!
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());

        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    }