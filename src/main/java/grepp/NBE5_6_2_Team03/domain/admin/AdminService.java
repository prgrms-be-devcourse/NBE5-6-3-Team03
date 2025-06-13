package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticProjection;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserQueryRepository;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.CannotModifyAdminException;
import grepp.NBE5_6_2_Team03.global.exception.CannotUpdateException;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final TravelPlanRepository travelPlanRepository;

    public Page<UserInfoResponse> findUsersPage(UserSearchRequest userSearchRequest) {
        boolean isLocked = userSearchRequest.isLocked();
        Pageable pageable = userSearchRequest.getPageable();
        Page<User> lockedUserInfos = userQueryRepository.findUsersByLockStatus(isLocked, pageable);
        return lockedUserInfos.map(UserInfoResponse::of);
    }

    @Transactional
    public void updateUserInfo(Long id, UserInfoUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new NotFoundException(Message.USER_NOT_FOUND)
        );

        user.updateProfile(
            request.getEmail(),
            request.getName(),
            request.getPhoneNumber(),
            null
        );
    }

    public boolean isDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByName(username).isPresent();
    }

    @Transactional
    public void lockUser(Long userId) {
        User user = findUserAndCheckAdminRole(userId);
        user.lock();
    }

    @Transactional
    public void unlockUser(Long userId) {
        User user = findUserAndCheckAdminRole(userId);
        user.unlock();
    }

    @Transactional
    public void deleteById(Long userId) {
        User user = findUserAndCheckAdminRole(userId);
        travelPlanRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    private User findUserAndCheckAdminRole(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));
        if(user.getRole() == Role.ROLE_ADMIN) {
            throw new CannotModifyAdminException(Message.ADMIN_NOT_MODIFIED);
        }
        return user;
    }

    public List<MonthlyStatisticResponse> getMonthStatistics() {
        List<MonthlyStatisticProjection> projections = travelPlanRepository.getMonthStatistics();
        return projections.stream()
            .map(p -> new MonthlyStatisticResponse(p.getMonth(), p.getCount()))
            .collect(Collectors.toList());
    }

    public List<CountriesStatisticResponse> getCountriesStatistics() {
        return travelPlanRepository.getCountriesStatistics();
    }

}