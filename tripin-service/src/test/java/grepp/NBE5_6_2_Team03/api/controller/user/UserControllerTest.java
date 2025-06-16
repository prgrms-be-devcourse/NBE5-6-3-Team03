package grepp.NBE5_6_2_Team03.api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.file.LocalStore;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.config.security.SecurityContextUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static grepp.NBE5_6_2_Team03.domain.user.Role.ROLE_USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WithMockUser
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LocalStore fileStore;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private SecurityContextUpdater updater;

    @DisplayName("회원 가입을 요청 할때 이메일 값이 잘못된다면 회원가입 예외가 발생한다")
    @Test
    void signUp_WhenEmailNotValidated_ThenThrowException() throws Exception {
        //given
        String wrongEmail = "a!1234@naver.com";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email(wrongEmail)
                .name("tester")
                .password("tempPassword!@")
                .phone("010-1234-5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 가입을 요청 할때 이름 값이 잘못된다면 회원가입 예외가 발생한다")
    @Test
    void signUp_WhenNameNotValidated_ThenThrowException() throws Exception {
        //given
        String wrongName = "testertestertester";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email("test@naver.com")
                .name(wrongName)
                .password("tempPassword!@")
                .phone("010-1234-5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 가입을 요청 할때 비밀번호에 특수문자 2개가 들어가지 않는다면 회원가입 예외가 발생한다")
    @Test
    void signUp_WhenPasswordNotValidated_ThenThrowException() throws Exception {
        //given
        String wrongPassword = "tempPassword";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email("test@naver.com")
                .name("tester")
                .password(wrongPassword)
                .phone("010-1234-5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 가입을 요청 할때 휴대폰 번호 양식이 맞지 않는다면 회원가입 예외가 발생한다")
    @Test
    void signUp_WhenPhoneNumberNotValidated_ThenThrowException() throws Exception {
        //given
        String wrongPhoneNumber = "0101234-5678";
        UserSignUpRequest request = UserSignUpRequest.builder()
                .email("test@naver.com")
                .name("tester")
                .password("tempPassword!@")
                .phone(wrongPhoneNumber)
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    private UserSignUpRequest createSignUpRequest(String email, String name) {
        return UserSignUpRequest.builder()
                .email(email)
                .name(name)
                .password("tempPassword!@")
                .phone("010-1234-5678")
                .build();
    }

    private UserEditRequest createModifyProfileRequest(String name, String email, MultipartFile file) {
        return UserEditRequest.builder()
                .name(name)
                .email(email)
                .phoneNumber("010-1234-5678")
                .rawPassword("tempPassword!@")
                .profileImage(file)
                .build();
    }

    private User createUserDetails() {
        return User.builder()
                .id(1L)
                .name("tester")
                .email("testEmail@naver.com")
                .password("tempPassword!@")
                .role(ROLE_USER)
                .build();
    }
}