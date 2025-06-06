package co.com.prueba.filmflix.application.controller;

import java.util.*;
import co.com.prueba.filmflix.application.service.IFilmService;
import co.com.prueba.filmflix.domain.dto.film.CreateFilmRequest;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.film.UpdateFilmRequest;
import co.com.prueba.filmflix.utils.enums.FilmGenre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/film")
@RestController
@Tag(name = "Manage movies", description = "Endpoints to manage the movies")
@SecurityRequirement(name = "bearer-key")
public class FilmController {
    private final IFilmService filmService;

    @Operation(summary = "Obtain enabled and non-enabled movies", description = "Allows you to view absolutely all the movies on the platform.")
    @GetMapping("/admin")
    public ResponseEntity<List<FilmResponse>> findAll() {
        return new ResponseEntity<>(filmService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtain enabled  movies", description = "Allows you to view absolutely all the enabled movies on the platform.")
    @GetMapping()
    public ResponseEntity<List<FilmResponse>> findAllEnabled() {
        return new ResponseEntity<>(filmService.findAllEnabled(), HttpStatus.OK);
    }

    @Operation(summary = "Search by title", description = "Allows you to search for movies with approximations to their titles.")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<FilmResponse>> findByTitle(@PathVariable String title) {
        return new ResponseEntity<>(filmService.findByTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Get movie with the ID", description = "Get a single movie with its identifier.")
    @GetMapping("/enabled/{id}")
    public ResponseEntity<FilmResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a movie", description = "Create a movie with the poster image.")
    @PostMapping(path = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FilmResponse> create(
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("duration") String duration,
            @RequestPart("filmGenre") String filmGenre,
            @RequestPart("trailerUrl") String trailerUrl,
            @RequestPart("filmStaff") String filmStaff,
            @RequestPart("poster") MultipartFile image
            ) throws IOException {
        FilmGenre genre = FilmGenre.valueOf(filmGenre);
        return new ResponseEntity<>(filmService.save(title,description, duration, genre, trailerUrl, filmStaff, image), HttpStatus.OK);
    }

    @Operation(summary = "Update movie", description = "Allows to update some fields of the movies.")
    @PutMapping(path = "/admin")
    public ResponseEntity<FilmResponse> update(
            @RequestBody @Valid UpdateFilmRequest updateFilmRequest
    ) throws IOException {
        return new ResponseEntity<>(filmService.update(updateFilmRequest), HttpStatus.OK);
    }

    @Operation(summary = "Disable film", description = "Disable a movie with its ID.")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
