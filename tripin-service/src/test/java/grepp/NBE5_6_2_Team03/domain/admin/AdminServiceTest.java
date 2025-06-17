package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("월별 여행계획 발생 통계 출력")
    @Transactional
    void getMonthStatistics() {
        // given <- in the DB
        // when
        List<MonthlyStatisticResponse> res = adminService.getMonthStatistics();

        // then
        assertFalse(res.isEmpty());
        assertTrue(res.size() <= 12);
    }

    @Test
    @DisplayName("도시별 여행 계획 통계 출력")
    @Transactional
    void getStatistic() {
        // given <- in the DB
        // when
        List<CountriesStatisticResponse> res = adminService.getCountriesStatistics();
        // then
        // TODO AssertJ
        for(CountriesStatisticResponse r : res) {
            System.out.println("OUT  Country: " + r.getCountry() + "-Count: " + r.getCount());
        }
    }

}