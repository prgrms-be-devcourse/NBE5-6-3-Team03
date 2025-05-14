package grepp.NBE5_6_2_Team03.domain.user.service;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.UserSignUpException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static grepp.NBE5_6_2_Team03.global.exception.Message.*;
import static grepp.NBE5_6_2_Team03.global.exception.Reason.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long signup(UserSignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity(encodedPassword);
        duplicatedEmailCheck(user);
        duplicatedNameCheck(user);

        return userRepository.save(user).getId();
    }

    public Boolean findByName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    public Boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void duplicatedEmailCheck(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(findUser -> {
                    throw new UserSignUpException(USER_EMAIL, USER_EMAIL_DUPLICATED);
                });
    }

    private void duplicatedNameCheck(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(
                        findUser -> {
                            throw new UserSignUpException(USER_NAME, USER_NAME_DUPLICATED);
                        }
                );
    }
}
