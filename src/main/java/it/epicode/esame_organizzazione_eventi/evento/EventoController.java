package it.epicode.esame_organizzazione_eventi.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Evento> createEvento(@RequestBody EventoDTO eventoDTO) {
        return new ResponseEntity<>(eventoService.createEvento(eventoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.modifyEvento(id, eventoDTO);
        return ResponseEntity.ok(evento);
    }


}
