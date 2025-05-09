package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}