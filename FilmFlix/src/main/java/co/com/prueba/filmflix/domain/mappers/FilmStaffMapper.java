package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.filmStaff.CreateFilmStaffRequest;
import co.com.prueba.filmflix.domain.dto.filmStaff.FilmStaffResponse;
import co.com.prueba.filmflix.domain.entities.FilmStaff;

import java.util.List;

public class FilmStaffMapper {
    public static FilmStaffResponse toFilmStaffResponse(FilmStaff filmStaff) {
        return FilmStaffResponse.builder()
                .id(filmStaff.getId())
                .staffRol(filmStaff.getStaffRol().toString())
                .name(filmStaff.getName())
                .build();
    }

    public static FilmStaff toFilmStaff(CreateFilmStaffRequest createFilmStaffRequest) {
        return FilmStaff.builder()
                .staffRol(createFilmStaffRequest.getStaffRol())
                .name(createFilmStaffRequest.getName())
                .build();
    }

    public static List<FilmStaffResponse> toFilmStaffResponseList(List<FilmStaff> filmStaff) {
        return filmStaff.stream().map(FilmStaffMapper::toFilmStaffResponse).toList();
    }
}
