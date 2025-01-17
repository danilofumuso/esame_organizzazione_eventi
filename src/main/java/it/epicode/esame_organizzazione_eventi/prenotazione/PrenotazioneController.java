package it.epicode.esame_organizzazione_eventi.prenotazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Prenotazione>> getAllPrenotazioni(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {

        return ResponseEntity.ok(prenotazioneService.findAllPrenotazioniUser(user.getUsername()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Prenotazione> createPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO,
                                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {

        return new ResponseEntity<>(prenotazioneService.createPrenotazione(prenotazioneDTO, user.getUsername()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePrenotazione(@PathVariable Long id,
                                                     @AuthenticationPrincipal org.springframework.security.core.userdetails.User user){

        prenotazioneService.deletePrenotazione(id, user.getUsername());
        return new ResponseEntity<>("Prenotazione eliminata con successo",HttpStatus.NO_CONTENT);
    }

}
