package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.film.CreateFilmRequest;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.film.UpdateFilmRequest;
import co.com.prueba.filmflix.utils.enums.FilmGenre;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFilmService {
    List<FilmResponse> findAll();
    List<FilmResponse> findAllEnabled();
    List<FilmResponse> findByTitle(String title) throws NotFoundException;
    FilmResponse findById(Long id) throws NotFoundException;
    FilmResponse save(String title, String description, String duration, FilmGenre filmGenre, String trailerUrl, String filmStaff, MultipartFile poster) throws IOException;
    FilmResponse update(UpdateFilmRequest updateFilmRequest);
    void delete(Long id);
}
