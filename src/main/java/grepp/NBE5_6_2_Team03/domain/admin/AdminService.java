package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
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

        // fixme Builder 패턴 적용
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
        if (user.isLocked()) {
            throw new CannotUpdateException(Message.ALREADY_LOCKED.getDescription());
        }
        user.updateIsLocked(true);
    }

    @Transactional
    public void unlockUser(Long userId) {
        User user = findUserAndCheckAdminRole(userId);
        if (user.isLocked() == false) {
            throw new CannotUpdateException(Message.ALREADY_UNLOCKED.getDescription());
        }
        user.updateIsLocked(false);
    }

    @Transactional
    public void deleteById(Long userId) {
        User user = findUserAndCheckAdminRole(userId);
        travelPlanRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    // fixme 단일 책임 원칙을 약화시킬 가능성이 있음 생각필요
    private User findUserAndCheckAdminRole(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));
        if(user.getRole() == Role.ROLE_ADMIN) {
            throw new CannotModifyAdminException(Message.ADMIN_NOT_MODIFIED);
        }
        return user;
    }

    public List<MonthlyStatisticResponse> getMonthStatistics() {

        // fixme aliasing 적용
        List<Object[]> monthlyStatsRaw = travelPlanRepository.getMonthStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = new ArrayList<>();
        for(Object[] obj : monthlyStatsRaw) {
            int month = (Integer) obj[0];
            long count = (Long) obj[1];
            monthlyStatisticResponses.add(new MonthlyStatisticResponse(month, count));
        }
        return monthlyStatisticResponses;
    }

    public List<CountriesStatisticResponse> getCountriesStatistics() {

        // fixme aliasing 적용
        List<Object[]> monthlyStatisticsMap = travelPlanRepository.getCountriesStatistics();
        List<CountriesStatisticResponse> countriesStatisticResponses = new ArrayList<>();
        for(Object[] obj : monthlyStatisticsMap) {
            String country = (String) obj[0];
            long count = (Long) obj[1];
            countriesStatisticResponses.add(new CountriesStatisticResponse(country, count));
        }
        return countriesStatisticResponses;
    }

}