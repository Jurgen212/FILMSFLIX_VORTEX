package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.ticket.CreateTicketRequest;
import co.com.prueba.filmflix.domain.dto.ticket.TicketResponse;
import co.com.prueba.filmflix.domain.dto.ticket.UpdateTicketRequest;
import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.domain.entities.Function;
import co.com.prueba.filmflix.domain.entities.Ticket;
import co.com.prueba.filmflix.domain.entities.User;

import java.util.List;

public class TicketMapper {
    public static TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .user(UserMapper.toUserResponse(ticket.getUser()))
                .film(FilmMapper.toFilmResponse(ticket.getFilm()))
                .function(FunctionMapper.toFunctionResponse(ticket.getFunction()))
                .paymentMethod(ticket.getPaymentMethod().toString())
                .quantity(ticket.getQuantity())
                .total(ticket.getTotal())
                .date(ticket.getDate())
                .build();
    }

    public static Ticket toTicket(CreateTicketRequest createTicketRequest, double total, User user, Film film, Function function) {
        return Ticket.builder()
                .user(user)
                .film(film)
                .function(function)
                .paymentMethod(createTicketRequest.getPaymentMethod())
                .quantity(createTicketRequest.getQuantity())
                .total(total)
                .build();
    }

    public static Ticket toTicket(UpdateTicketRequest updateTicketRequest, double total, User user, Film film, Function function) {
        return Ticket.builder()
                .id(updateTicketRequest.getUserId())
                .user(user)
                .film(film)
                .function(function)
                .paymentMethod(updateTicketRequest.getPaymentMethod())
                .quantity(updateTicketRequest.getQuantity())
                .total(total)
                .build();
    }

    public static List<TicketResponse> toTicketResponseList(List<Ticket> tickets){
        return tickets.stream().map(TicketMapper::toTicketResponse).toList();
    }
}
