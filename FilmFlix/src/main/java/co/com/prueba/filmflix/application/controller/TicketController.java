package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.ITicketService;
import co.com.prueba.filmflix.domain.dto.function.CreateFunctionRequest;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.ticket.CreateTicketRequest;
import co.com.prueba.filmflix.domain.dto.ticket.TicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/ticket")
@RestController
@Tag(name = "Operate user tickets", description = "Endpoints to manage the tickets purchased.")
@SecurityRequirement(name = "bearer-key")
public class TicketController {
    private final ITicketService ticketService;

    @Operation(summary = "Obtain tickets", description = "Allows you to view absolutely all the tickets purchased on the platform.")
    @GetMapping("/admin")
    public ResponseEntity<List<TicketResponse>> findAll() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a ticket with the ID", description = "Get a single ticket with its identifier.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<TicketResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all tickets with User ID", description = "Get tickets with user identifier.")
    @GetMapping("/admin/user/{id}")
    public ResponseEntity<List<TicketResponse>> findByUser_Id(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.findAllByUser_Id(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all tickets with film ID", description = "Get tickets with film identifier.")
    @GetMapping("/admin/film/{id}")
    public ResponseEntity<List<TicketResponse>> findByFilm_Id(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.findAllByFilm_Id(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a ticket", description = "Create a ticket for a function.")
    @PostMapping("/buy")
    public ResponseEntity<TicketResponse> save(
            @RequestBody @Valid CreateTicketRequest createTicketRequest
    ){
        return new ResponseEntity<>(ticketService.create(createTicketRequest), HttpStatus.OK);
    }
}
