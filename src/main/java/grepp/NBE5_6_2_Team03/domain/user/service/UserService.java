package grepp.NBE5_6_2_Team03.domain.user.service;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.UserMyPageResponse;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.exception.InvalidPasswordException;
import grepp.NBE5_6_2_Team03.domain.user.file.FileStore;
import grepp.NBE5_6_2_Team03.domain.user.file.UploadFile;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.domain.user.exception.UserSignUpException;
import grepp.NBE5_6_2_Team03.global.config.security.SecurityContextUpdater;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static grepp.NBE5_6_2_Team03.global.exception.Message.*;
import static grepp.NBE5_6_2_Team03.global.exception.Reason.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileStore fileStore;
    private final SecurityContextUpdater securityContextUpdater;

    @Transactional
    public Long signup(UserSignUpRequest request) {
        if(isDuplicateUserInfo(request.getEmail(), request.getName())){
            throw new UserSignUpException(SIGN_UP, SIGNUP_DUPLICATE_ERROR);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity(encodedPassword);
        return userRepository.save(user).getId();
    }

    @Transactional
    public UserMyPageResponse updateProfile(UserEditRequest request, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if(isNotMatchPassword(user.getPassword(), request.getRawPassword())){
            throw new InvalidPasswordException(USER_NOT_MATCH_PASSWORD);
        }

        UploadFile uploadFile = fileStore.storeFile(request.getProfileImage());
        modifyProfile(request, user, uploadFile);
        securityContextUpdater.updateAuthentication(user, request.getRawPassword());

        return UserMyPageResponse.from(user);
    }

    public UserMyPageResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return UserMyPageResponse.from(user);
    }

    public boolean isNotDuplicatedName(String name) {
        return !isDuplicatedName(name);
    }

    public boolean isNotDuplicatedEmail(String email) {
        return !isDuplicatedEmail(email);
    }

    public String getFileDir() {
        return fileStore.getFileDir();
    }

    private void modifyProfile(UserEditRequest request, User user, UploadFile uploadFile) {
        user.updateProfile(
                request.getEmail(),
                request.getName(),
                request.getPhoneNumber(),
                uploadFile
        );
    }

    private boolean isDuplicateUserInfo(String email, String name){
        return isDuplicatedEmail(email) || isDuplicatedName(name);
    }

    private boolean isDuplicatedName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    private boolean isDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isNotMatchPassword(String encodedPassword, String rawPassword) {
        return !passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
