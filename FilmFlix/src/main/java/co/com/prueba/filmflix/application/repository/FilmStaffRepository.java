package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.FilmStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmStaffRepository extends JpaRepository<FilmStaff, Long> {
    boolean existsByName(String name);
    List<FilmStaff> findAllByEnabledTrue();
}
