package co.com.prueba.filmflix.application.service;
import co.com.prueba.filmflix.domain.entities.Ticket;
import co.com.prueba.filmflix.domain.entities.User;
import org.thymeleaf.context.Context;

public interface IMailService {
    void sendConfirmationEmailTicket(Ticket ticket);
    void sendConfirmationEmailRegister(User user);
}
