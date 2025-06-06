package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByEnabledTrue();
    boolean existsByTitle(String title);
    List<Film> findByTitleContainingIgnoreCase(String title);
}
