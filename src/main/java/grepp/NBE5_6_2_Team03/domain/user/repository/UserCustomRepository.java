package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> findByIsLocked(boolean isLocked, Pageable pageable);
}
