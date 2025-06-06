package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.ticket.CreateTicketRequest;
import co.com.prueba.filmflix.domain.dto.ticket.TicketResponse;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;

import java.util.List;

public interface ITicketService {
    List<TicketResponse> findAll();
    TicketResponse findById(Long id) throws NotFoundException;
    List<TicketResponse> findAllByUser_Id(Long userId) throws NotFoundException;
    List<TicketResponse> findAllByFilm_Id(Long filmId) throws NotFoundException;
    TicketResponse create(CreateTicketRequest createTicketRequest) throws NotFoundException;
}
