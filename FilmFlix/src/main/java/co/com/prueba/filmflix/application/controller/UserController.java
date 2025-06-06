package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.IUserService;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import co.com.prueba.filmflix.domain.dto.ticket.CreateTicketRequest;
import co.com.prueba.filmflix.domain.dto.ticket.TicketResponse;
import co.com.prueba.filmflix.domain.dto.user.UpdateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Tag(name = "User", description = "Endpoints for managing users")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    private final IUserService userService;

    @Operation(summary = "Get all users", description = "Obtain a list of all users.")
    @GetMapping("/admin")
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtain enabled users", description = "List of all enabled users.")
    @GetMapping("/admin/enabled")
    public ResponseEntity<List<UserResponse>> findAllEnabled() {
        return new ResponseEntity<>(userService.findAllEnabled(), HttpStatus.OK);
    }

    @Operation(summary = "Get user by id", description = "Obtain a user by their unique id.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update the user rol", description = "Update the user Rol with his unique id.")
    @PostMapping("/admin/updaterol/{id}")
    public ResponseEntity<UserResponse> updateRol(@PathVariable Long id) {
        return new ResponseEntity<>(userService.updateRol(id), HttpStatus.OK);
    }

    @Operation(summary = "Update a user", description = "Allows to update some fields of the users.")
    @PutMapping(path = "/admin")
    public ResponseEntity<UserResponse> update(
            @RequestBody @Valid UpdateUserRequest updateUserRequest
    ) throws IOException {
        return new ResponseEntity<>(userService.update(updateUserRequest), HttpStatus.OK);
    }

    @Operation(summary = "Disable a user", description = "Disable a user with its ID.")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
