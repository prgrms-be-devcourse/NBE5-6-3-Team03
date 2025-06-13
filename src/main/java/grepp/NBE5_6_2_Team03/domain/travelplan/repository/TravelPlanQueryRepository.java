package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticProjection;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

import static grepp.NBE5_6_2_Team03.domain.expense.QExpense.*;
import static grepp.NBE5_6_2_Team03.domain.travelplan.QTravelPlan.*;
import static grepp.NBE5_6_2_Team03.domain.travelschedule.QTravelSchedule.*;

@Repository
public class TravelPlanQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public TravelPlanQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public TravelPlan getTravelPlanFetchScheduleAndExpense(Long travelPlanId){
        return queryFactory.selectDistinct(travelPlan)
                .from(travelPlan)
                .leftJoin(travelPlan.travelSchedules, travelSchedule).fetchJoin()
                .leftJoin(travelSchedule.expense, expense).fetchJoin()
                .where(travelPlan.travelPlanId.eq(travelPlanId))
                .fetchOne();
    }

    // TODO QueryDSL 공부하기
    public List<CountriesStatisticResponse> getCountriesStatistics() {
        return queryFactory
            .select(Projections.constructor(CountriesStatisticResponse.class,
                travelPlan.country,
                travelPlan.travelPlanId.count()
            ))
            .from(travelPlan)
            .groupBy(travelPlan.country)
            .fetch();
    }
    public List<MonthlyStatisticProjection> getMonthStatistics() {
        int currentYear = LocalDate.now().getYear();

        return queryFactory
            .select(Projections.fields(MonthlyStatisticProjection.class,
                Expressions.dateTimeTemplate(Integer.class, "month({0})", travelPlan.travelStartDate).as("month"),
                travelPlan.travelPlanId.count().as("count")
            ))
            .from(travelPlan)
            .where(Expressions.dateTimeTemplate(Integer.class, "year({0})", travelPlan.travelStartDate).eq(currentYear))
            .groupBy(Expressions.dateTimeTemplate(Integer.class, "month({0})", travelPlan.travelStartDate))
            .orderBy(Expressions.dateTimeTemplate(Integer.class, "month({0})", travelPlan.travelStartDate).asc())
            .fetch();
    }

}
