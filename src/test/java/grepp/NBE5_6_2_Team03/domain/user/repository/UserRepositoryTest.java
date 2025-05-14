package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("회원 이메일을 통해 회원을 조회 합니다.")
    @Test
    void findByEmail() {
        //given
        User user = createUser("test@naver.com", "테스터");
        userRepository.save(user);
        em.flush(); em.clear();

        //when
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NoSuchElementException());

        //then
        assertThat(findUser.getName()).isEqualTo("테스터");
    }

    @DisplayName("회원 이름을 통해 회원을 조회 합니다.")
    @Test
    void findByName() {
        //given
        User user = createUser("test@naver.com", "테스터");
        userRepository.save(user);
        em.flush(); em.clear();

        //when
        User findUser = userRepository.findByName(user.getName())
                .orElseThrow(() -> new NoSuchElementException());

        //then
        assertThat(findUser.getName()).isEqualTo("테스터");
    }

    private User createUser(String email, String name){
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }
}