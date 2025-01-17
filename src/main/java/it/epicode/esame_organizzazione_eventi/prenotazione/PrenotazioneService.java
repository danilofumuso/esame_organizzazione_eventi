package it.epicode.esame_organizzazione_eventi.prenotazione;

import it.epicode.esame_organizzazione_eventi.auth.AppUserRepository;
import it.epicode.esame_organizzazione_eventi.evento.Evento;
import it.epicode.esame_organizzazione_eventi.evento.EventoRepository;
import it.epicode.esame_organizzazione_eventi.exceptions.EventoNotFoundException;
import it.epicode.esame_organizzazione_eventi.exceptions.PrenotazioneNonEffettuabileException;
import it.epicode.esame_organizzazione_eventi.exceptions.PrenotazioneNotFoundException;
import it.epicode.esame_organizzazione_eventi.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public List<Prenotazione> findAllPrenotazioniUser(String username) {
        return appUserRepository.findPrenotazioneByUserId(appUserRepository.findByUsername(username).get().getId());
    }

    public Prenotazione createPrenotazione(@Valid PrenotazioneDTO prenotazioneDTO, String username) {
        Prenotazione prenotazione = new Prenotazione();
        BeanUtils.copyProperties(prenotazioneDTO, prenotazione);

        prenotazione.setDataPrenotazione(LocalDate.now());
        Evento evento = eventoRepository.findById(prenotazioneDTO.getEventoId())
                .orElseThrow(() -> new EventoNotFoundException("Evento non trovato!"));
        prenotazione.setEvento(evento);

        prenotazione.setUser(appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User non trovato!")));
        prenotazione.setNumeroPosti(prenotazioneDTO.getNumeroPosti());

        if (evento.getPostiDisponibili() < prenotazioneDTO.getNumeroPosti()) {
            throw new PrenotazioneNonEffettuabileException("Posti non disponibili");
        }
        evento.setPostiDisponibili(evento.getPostiDisponibili() - prenotazioneDTO.getNumeroPosti());
        eventoRepository.save(evento);
        return prenotazioneRepository.save(prenotazione);
    }

    public void deletePrenotazione(Long id, String username){

      Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException("Prenotazione non trovata!"));

        if (!prenotazione.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("User non autorizzato!");
        }

        Evento evento= eventoRepository.findById(prenotazione.getEvento().getId())
                .orElseThrow(() -> new EventoNotFoundException("Evento non trovato!"));

        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getNumeroPosti());
        eventoRepository.save(evento);

       prenotazioneRepository.delete(prenotazione);
    }

}
