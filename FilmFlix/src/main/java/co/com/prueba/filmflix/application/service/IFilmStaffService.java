package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.filmStaff.CreateFilmStaffRequest;
import co.com.prueba.filmflix.domain.dto.filmStaff.FilmStaffResponse;

import java.util.List;

public interface IFilmStaffService {
    List<FilmStaffResponse> findAll();
    List<FilmStaffResponse> findAllEnabled();
    FilmStaffResponse findById(Long id);
    FilmStaffResponse save(CreateFilmStaffRequest createFilmStaffRequest);
    void delete(Long id);
}
