package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.IFilmStaffService;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.filmStaff.CreateFilmStaffRequest;
import co.com.prueba.filmflix.domain.dto.filmStaff.FilmStaffResponse;
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
@RequestMapping("/staff")
@RestController
@Tag(name = "Handling movie characters (STAFF)", description = "Endpoints to manage the movies staff.")
@SecurityRequirement(name = "bearer-key")
public class FilmStaffController {
    private final IFilmStaffService filmStaffService;

    @Operation(summary = "Obtain enabled and non-enabled characters", description = "Allows you to view absolutely all the characters on the platform.")
    @GetMapping("/admin")
    public ResponseEntity<List<FilmStaffResponse>> findAll() {
        return new ResponseEntity<>(filmStaffService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtain enabled  characters", description = "Allows you to view absolutely all the enabled characters on the platform.")
    @GetMapping()
    public ResponseEntity<List<FilmStaffResponse>> findAllEnabled() {
        return new ResponseEntity<>(filmStaffService.findAllEnabled(), HttpStatus.OK);
    }

    @Operation(summary = "Get a character with the ID", description = "Get a single character (STAFF) with its identifier.")
    @GetMapping("/enabled/{id}")
    public ResponseEntity<FilmStaffResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(filmStaffService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a staff character", description = "Create a staff character.")
    @PostMapping(path = "/admin")
    public ResponseEntity<FilmStaffResponse> save(
            @RequestBody @Valid CreateFilmStaffRequest createFilmStaffRequest
    ) throws IOException {
        return new ResponseEntity<>(filmStaffService.save(createFilmStaffRequest), HttpStatus.OK);
    }

    @Operation(summary = "Disable character", description = "Disable a character (STAFF) with its ID.")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmStaffService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
