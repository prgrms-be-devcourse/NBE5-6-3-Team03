package grepp.NBE5_6_2_Team03.domain.user.service;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.command.UserCreateCommand;
import grepp.NBE5_6_2_Team03.domain.user.command.UserEditCommand;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.domain.user.exception.UserSignUpException;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grepp.NBE5_6_2_Team03.global.exception.Message.*;
import static grepp.NBE5_6_2_Team03.global.exception.Reason.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long signup(UserCreateCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = User.register(command, encodedPassword);
        duplicatedEmailCheck(user);
        duplicatedNameCheck(user);

        return userRepository.save(user).getId();
    }

    public User editUser(UserEditCommand editCommand, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원을 찾지 못했습니다."));

        user.edit(editCommand);
        return user;
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
