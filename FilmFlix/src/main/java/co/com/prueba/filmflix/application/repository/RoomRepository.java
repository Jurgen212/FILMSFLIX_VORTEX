package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByEnabledTrue();
    boolean existsRoomByName(String name);
}
