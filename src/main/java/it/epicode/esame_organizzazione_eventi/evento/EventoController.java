package it.epicode.esame_organizzazione_eventi.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventi() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/eventi_organizzatore")
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Evento>> getAllEventiOrganizzatore(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return ResponseEntity.ok(eventoService.findAllEventiOrganizzatore(user.getUsername()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Evento> createEvento(@RequestBody EventoDTO eventoDTO,
                                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return new ResponseEntity<>(eventoService.createEvento(eventoDTO, user.getUsername()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id,
                                               @RequestBody EventoDTO eventoDTO,
                                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        Evento evento = eventoService.modifyEvento(id, eventoDTO, user.getUsername());
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEvento(@PathVariable Long id,
                                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        eventoService.deleteEvento(id, user.getUsername());
        return new ResponseEntity<>("Evento Eliminato con successo", HttpStatus.NO_CONTENT);
    }

}
