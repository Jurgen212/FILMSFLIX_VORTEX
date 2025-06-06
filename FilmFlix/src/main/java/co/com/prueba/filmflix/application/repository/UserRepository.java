package co.com.prueba.filmflix.application.repository;

import co.com.prueba.filmflix.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEnabledTrue();
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    User findByEmail(String email);
}
