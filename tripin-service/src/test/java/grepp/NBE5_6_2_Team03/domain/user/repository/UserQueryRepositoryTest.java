package grepp.NBE5_6_2_Team03.domain.user.repository;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.TestQueryDsl;
import grepp.NBE5_6_2_Team03.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserQueryRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryRepository userQueryRepository;

    @DisplayName("쿼리 DSL 실행 체크 테스트")
    @Test
    void queryDslTest() {
        //given
        User user = createUser("test@naver.com", "테스터");
        userRepository.save(user);

        //when
        TestQueryDsl result = userQueryRepository.queryDslTest(user.getId());

        //then
        assertThat(result.getName()).isEqualTo("테스터");
        assertThat(result.getEmail()).isEqualTo("test@naver.com");
    }

    private User createUser(String email, String name){
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }
}