package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.FilmRepository;
import co.com.prueba.filmflix.application.repository.FunctionRepository;
import co.com.prueba.filmflix.application.repository.TicketRepository;
import co.com.prueba.filmflix.application.repository.UserRepository;
import co.com.prueba.filmflix.application.service.IMailService;
import co.com.prueba.filmflix.application.service.ITicketService;
import co.com.prueba.filmflix.domain.dto.ticket.CreateTicketRequest;
import co.com.prueba.filmflix.domain.dto.ticket.TicketResponse;
import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.domain.entities.Function;
import co.com.prueba.filmflix.domain.entities.Ticket;
import co.com.prueba.filmflix.domain.entities.User;
import co.com.prueba.filmflix.domain.mappers.TicketMapper;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.TicketValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements ITicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FunctionRepository functionRepository;
    private final FilmRepository filmRepository;
    private final IMailService mailService;

    @Override
    public List<TicketResponse> findAll() {
        return TicketMapper.toTicketResponseList(ticketRepository.findAll());
    }

    @Override
    public TicketResponse findById(Long id) throws NotFoundException{
        if(!ticketRepository.existsById(id)) throw new NotFoundException(String.format(TicketValidator.NOT_FOUND_BY_ID, id));
        return TicketMapper.toTicketResponse(ticketRepository.getReferenceById(id));
    }

    @Override
    public List<TicketResponse> findAllByUser_Id(Long userId) throws NotFoundException{
        if(!userRepository.existsById(userId)) throw new NotFoundException(String.format(TicketValidator.TICKET_USER_NOT_FOUND_FUNCTION, userId));

        return TicketMapper.toTicketResponseList(ticketRepository.findAllByUser_Id(userId));
    }

    @Override
    public List<TicketResponse> findAllByFilm_Id(Long filmId) throws NotFoundException{
        if(!filmRepository.existsById(filmId)) throw new NotFoundException(String.format(TicketValidator.FILM_NOT_FOUND_TICKET, filmId));

        return TicketMapper.toTicketResponseList(ticketRepository.findAllByFilm_Id(filmId));
    }

    @Override
    public TicketResponse create(CreateTicketRequest createTicketRequest) throws NotFoundException{
        if(!userRepository.existsById(createTicketRequest.getUserId())) throw new NotFoundException(String.format(TicketValidator.TICKET_USER_NOT_FOUND_FUNCTION, createTicketRequest.getUserId()));
        if(!functionRepository.existsById(createTicketRequest.getFunctionId())) throw new NotFoundException(String.format(TicketValidator.FUNCTION_NOT_FOUND_TICKET, createTicketRequest.getFunctionId()));
        if(!filmRepository.existsById(createTicketRequest.getFilmId())) throw new NotFoundException(String.format(TicketValidator.FILM_NOT_FOUND_TICKET, createTicketRequest.getFilmId()));

        User user = userRepository.getReferenceById(createTicketRequest.getUserId());
        Function function = functionRepository.getReferenceById(createTicketRequest.getFunctionId());
        Film film = filmRepository.getReferenceById(createTicketRequest.getFilmId());

        double totalPrice = calculateTotalPrice(function.getFunctionPrice(), createTicketRequest.getQuantity());
        Ticket ticket = TicketMapper.toTicket(createTicketRequest, totalPrice, user, film, function);

        //mailService.sendConfirmationEmailTicket(ticket);
        return TicketMapper.toTicketResponse(ticketRepository.save(ticket));
    }


    private static double calculateTotalPrice(double functionPrice, Integer quantity){
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException(TicketValidator.QUANTITY_VALID);
        return functionPrice * quantity;
    }


}
