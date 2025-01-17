package it.epicode.esame_organizzazione_eventi.auth;


import it.epicode.esame_organizzazione_eventi.evento.Evento;
import it.epicode.esame_organizzazione_eventi.prenotazione.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT e FROM Evento e WHERE e.organizzatore.id = :organizzatoreId")
    List<Evento> findEventiByOrganizzatoreId(@Param("organizzatoreId") Long organizzatoreId);

    @Query("SELECT p FROM Prenotazione p WHERE p.user.id = :userId")
    List<Prenotazione> findPrenotazioneByUserId(@Param("userId") Long userId);

}
