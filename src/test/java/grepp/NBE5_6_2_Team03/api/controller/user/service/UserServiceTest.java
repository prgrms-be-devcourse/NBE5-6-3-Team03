package grepp.NBE5_6_2_Team03.api.controller.user.service;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.command.UserCreateCommand;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.domain.user.exception.UserSignUpException;
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
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 가입을 합니다.")
    @Test
    void signup() {
        //given
        UserCreateCommand request = createSignUpCommand("test@naver.com", "테스터");

        //when
        Long savedUserId = userService.signup(request);

        //then
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
        assertThat(findUser.getId()).isEqualTo(savedUserId);
    }

    @DisplayName("회원 가입을 할때 중복된 이메일이 있다면 예외가 발생 합니다.")
    @Test
    void signup_WhenDuplicatedEmail_ThenThrowException() {
        //given
        String duplicatedEmail = "test@naver.com";
        User user = createUser(duplicatedEmail, "회원");
        userRepository.save(user);

        UserCreateCommand request = createSignUpCommand(duplicatedEmail, "테스터");

        //when //then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(UserSignUpException.class)
                .hasMessage("회원 이메일이 이미 사용중 입니다.");
    }

    @DisplayName("회원 가입을 할때 중복된 이름이 있다면 예외가 발생 합니다.")
    @Test
    void signup_WhenDuplicatedName_ThenThrowException() {
        //given
        String duplicatedName = "회원";
        User user = createUser("abc@naver.com", duplicatedName);
        userRepository.save(user);

        UserCreateCommand request = createSignUpCommand("test@naver.com", duplicatedName);

        //when //then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(UserSignUpException.class)
                .hasMessage("회원 이름이 이미 사용중 입니다.");
    }

    private UserCreateCommand createSignUpCommand(String email, String name){
        return UserCreateCommand.builder()
                .email(email)
                .name(name)
                .password("tempPassword")
                .phoneNumber("010-1234-5678")
                .build();
    }

    private User createUser(String email, String name){
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }
}