package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.Function;
import co.com.prueba.filmflix.utils.enums.FunctionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {
    List<Function> findAllByEnabledTrueAndFilmId(Long filmId);
    List<Function> findAllByTime(FunctionTime time);
    List<Function> findAllByEnabledTrue();
    boolean existsByRoom_IdAndTimeAndEnabled(Long roomId, FunctionTime time, boolean enabled);
}
