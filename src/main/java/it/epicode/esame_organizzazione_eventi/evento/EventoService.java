package it.epicode.esame_organizzazione_eventi.evento;

import it.epicode.esame_organizzazione_eventi.auth.AppUserRepository;
import it.epicode.esame_organizzazione_eventi.exceptions.EventoNotFoundException;
import it.epicode.esame_organizzazione_eventi.exceptions.OrganizzatoreNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Evento createEvento(@Valid EventoDTO eventoDTO) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDTO, evento);
        evento.setOrganizzatore(appUserRepository.findById(eventoDTO.getOrganizzatoreId())
                .orElseThrow(() -> new OrganizzatoreNotFoundException("Organizzatore non trovato!")));
        return eventoRepository.save(evento);
    }

    public Evento modifyEvento(@Valid Long id, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new EventoNotFoundException("Evento non trovato!"));
        BeanUtils.copyProperties(eventoDTO, evento);
        return eventoRepository.save(evento);
    }

}
