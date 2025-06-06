package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.film.CreateFilmRequest;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.film.UpdateFilmRequest;
import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.domain.entities.FilmCast;
import co.com.prueba.filmflix.domain.entities.FilmStaff;

import java.util.List;

public class FilmMapper {
    public static FilmResponse toFilmResponse(Film film) {
        return FilmResponse.builder()
                .id(film.getId())
                .title(film.getTitle())
                .description(film.getDescription())
                .duration(film.getDuration())
                .filmGenre(film.getFilmGenre().toString())
                .posterUrl(film.getPosterUrl())
                .trailerUrl(film.getTrailerUrl())
                .filmStaff(film.getFilmCastList().stream()
                        .map(cast -> FilmStaffMapper.toFilmStaffResponse(cast.getFilmStaff())
                        ).toList()
                )
                .enabled(film.isEnabled())
                .build();
    }

    public static Film toFilm(CreateFilmRequest createFilmRequest, String posterUrl){
        return Film.builder()
                .title(createFilmRequest.getTitle())
                .description(createFilmRequest.getDescription())
                .duration(createFilmRequest.getDuration())
                .filmGenre(createFilmRequest.getFilmGenre())
                .posterUrl(posterUrl)
                .trailerUrl(createFilmRequest.getTrailerUrl())
                .build();
    }

    public static Film toFilm(UpdateFilmRequest updateFilmRequest, String posterUrl, boolean enabled){
        return Film.builder()
                .id(updateFilmRequest.getId())
                .title(updateFilmRequest.getTitle())
                .description(updateFilmRequest.getDescription())
                .duration(updateFilmRequest.getDuration())
                .filmGenre(updateFilmRequest.getFilmGenre())
                .posterUrl(posterUrl)
                .trailerUrl(updateFilmRequest.getTrailerUrl())
                .enabled(enabled)
                .build();
    }

    public static List<FilmResponse> toFilmResponseList(List<Film> film){
        return film.stream().map(FilmMapper::toFilmResponse).toList();
    }
}
