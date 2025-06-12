package grepp.NBE5_6_2_Team03.api.controller.user.service;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.UserMyPageResponse;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.domain.user.exception.UserSignUpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 가입을 합니다.")
    @Test
    void signup() {
        //given
        UserSignUpRequest request = createSignUpRequest("test@naver.com", "테스터");

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
        User user = createUser(duplicatedEmail, "tempPassword!@", "회원");
        userRepository.save(user);

        UserSignUpRequest request = createSignUpRequest(duplicatedEmail, "테스터");

        //when //then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(UserSignUpException.class)
                .hasMessage("회원 이메일 혹은 이름이 중복 사용중 입니다.");
    }

    @DisplayName("회원 가입을 할때 중복된 이름이 있다면 예외가 발생 합니다.")
    @Test
    void signup_WhenDuplicatedName_ThenThrowException() {
        //given
        String duplicatedName = "회원";
        User user = createUser("abc@naver.com", "tempPassword!@", duplicatedName);
        userRepository.save(user);

        UserSignUpRequest request = createSignUpRequest("test@naver.com", duplicatedName);

        //when //then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(UserSignUpException.class)
                .hasMessage("회원 이메일 혹은 이름이 중복 사용중 입니다.");
    }

    @DisplayName("회원 프로필의 이름을 업데이트 한다.")
    @Test
    void updateProfile() throws IOException {
        //given
        String name = "테스터";
        User user = createUser("test@naver.com", "tempPassword!@", name);
        userRepository.save(user);

        UserEditRequest request = createUserEditRequest("test@naver.com", "tempPassword!@", name + "hi");

        //when
        UserMyPageResponse response = userService.updateProfile(request, user.getId());

        //then
        assertThat(response.getName()).isEqualTo("테스터hi");
    }

    private UserSignUpRequest createSignUpRequest(String email, String name){
        return UserSignUpRequest.builder()
                .email(email)
                .name(name)
                .password("tempPassword")
                .phoneNumber("010-1234-5678")
                .build();
    }

    private UserEditRequest createUserEditRequest(String email, String rawPassword, String name){
        return UserEditRequest.builder()
                .email(email)
                .rawPassword(rawPassword)
                .name(name)
                .profileImage(null)
                .build();
    }

    private User createUser(String email, String password, String name){
        return User.builder()
                .email(email)
                .name(name)
                .password(bCryptPasswordEncoder.encode(password))
                .build();
    }
}