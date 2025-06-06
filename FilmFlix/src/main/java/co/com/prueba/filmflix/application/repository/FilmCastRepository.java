package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.FilmCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmCastRepository extends JpaRepository<FilmCast, Long> {
    List<FilmCast> findAllByFilm_Id(Long filmId);
}
