package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.file.FileStore;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.config.security.SecurityContextUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static grepp.NBE5_6_2_Team03.domain.user.Role.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WithMockUser
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStore fileStore;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private SecurityContextUpdater updater;

    @DisplayName("회원 가입 폼을 요청할 때 회원 가입 폼으로 이동 한다.")
    @Test
    void signUpForm() throws Exception {
        //given && when && then
        mockMvc.perform(
                        get("/users/sign-up")
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/user/signup-form"));
    }

    @DisplayName("회원 가입을 요청 할때 성공 한다면 메인 화면으로 리다이랙트 한다.")
    @Test
    void signUp() throws Exception {
        //given
        UserSignUpRequest request = createSignUpRequest("test@naver.com", "테스터");

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("회원 가입을 요청 할때 실패 한다면 회원가입 폼으로 돌아 간다.")
    @Test
    void signUp_WhenFailedRequest_ThenReturnSignUpForm() throws Exception {
        //given
        UserSignUpRequest request = createSignUpRequest("test@naver.com", null);

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/user/signup-form"));
    }

    @DisplayName("회원 가입을 요청 할때 이메일 값이 잘못된다면 회원가입 예외가 발생한다")
    @Test
    void signUp_WhenEmailNotValidated_ThenThrowException() throws Exception {
        //given
        String wrongEmail = "a!1234@naver.com";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email(wrongEmail)
                .name("tester")
                .password("tempPassword!@")
                .phoneNumber("010-1234-5678")
                .build();

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userSignUpRequest", "email"));
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
                .phoneNumber("010-1234-5678")
                .build();

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userSignUpRequest", "name"));
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
                .phoneNumber("010-1234-5678")
                .build();

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userSignUpRequest", "password"));
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
                .phoneNumber(wrongPhoneNumber)
                .build();

        //when //then
        mockMvc.perform(
                        post("/users/sign-up")
                                .param("email", request.getEmail())
                                .param("password", request.getPassword())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userSignUpRequest", "phoneNumber"));
    }

    @DisplayName("회원 마이페이지로 이동 한다.")
    @Test
    void myPage() throws Exception {
        //given && when && then
        mockMvc.perform(
                        get("/users/my-page")
                                .with(csrf())
                                .with(user(new CustomUserDetails(createUserDetails())))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/my-page"));
    }

    @DisplayName("회원 수정에 성공 할때 회원 수정 폼으로 리다이랙트 한다.")
    @Test
    void modifyProfileUser() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "profileImage",
                "test-image.jpg",
                "image/jpeg",
                "fake image content".getBytes(StandardCharsets.UTF_8)
        );

        UserEditRequest request = createModifyProfileRequest("tester", "test@naver.com", file);

        //when //then
        mockMvc.perform(
                        multipart("/users/edit")
                                .file(file)
                                .param("email", request.getEmail())
                                .param("rawPassword", request.getRawPassword())
                                .param("email", request.getEmail())
                                .param("name", request.getName())
                                .param("phoneNumber", request.getPhoneNumber())
                                .with(csrf())
                                .with(user(new CustomUserDetails(createUserDetails())))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/my-page"));
    }

    private UserSignUpRequest createSignUpRequest(String email, String name) {
        return UserSignUpRequest.builder()
                .email(email)
                .name(name)
                .password("tempPassword!@")
                .phoneNumber("010-1234-5678")
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