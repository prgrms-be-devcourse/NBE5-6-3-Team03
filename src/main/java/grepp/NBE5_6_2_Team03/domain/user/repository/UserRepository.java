package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    @Modifying
    @Query("update User u set u.isLocked = true where u.id = :id")
    void lockUser(@Param("id") Long id);

    @Query("select u.role from User u where u.id = :id")
    String getRoleById(@Param("id") Long id);

    @Modifying
    @Query("update User u set u.activated = false where u.id = :id")
    void deactivateById(@Param("id") Long id);

}