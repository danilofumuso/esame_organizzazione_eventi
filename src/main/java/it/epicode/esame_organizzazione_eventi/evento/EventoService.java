package it.epicode.esame_organizzazione_eventi.evento;

import it.epicode.esame_organizzazione_eventi.auth.*;
import it.epicode.esame_organizzazione_eventi.exceptions.EventoNotFoundException;
import it.epicode.esame_organizzazione_eventi.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public List<Evento> findAllEventiOrganizzatore(String username){
        return appUserRepository.findEventiByOrganizzatoreId(appUserRepository.findByUsername(username).get().getId());
    }

    public Evento createEvento(@Valid EventoDTO eventoDTO, String username) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDTO, evento);
        evento.setOrganizzatore(appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Organizzatore non trovato!")));

        return eventoRepository.save(evento);
    }

    public Evento modifyEvento(@Valid Long id, @Valid EventoDTO eventoDTO, String username){
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new EventoNotFoundException("Evento non trovato!"));
        BeanUtils.copyProperties(eventoDTO, evento);

        if (!evento.getOrganizzatore().getUsername().equals(username)) {
            throw new AccessDeniedException("Organizzatore non autorizzato!");
        }
        return eventoRepository.save(evento);
    }

    public void deleteEvento(@Valid Long id, String username){

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNotFoundException("Evento non trovato!"));

        if (!evento.getOrganizzatore().getUsername().equals(username)) {
            throw new AccessDeniedException("Organizzatore non autorizzato!");
        }
        eventoRepository.delete(evento);
    }


}
