package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    @Query("select case when u.role = 'ROLE_ADMIN' then true else false end from User u where u.id = :id")
    boolean isAdmin(@Param("id") Long id);
}