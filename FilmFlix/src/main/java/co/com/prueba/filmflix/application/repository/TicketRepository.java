package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUser_Id(Long userId);
    List<Ticket> findAllByFilm_Id(Long filmId);
}
