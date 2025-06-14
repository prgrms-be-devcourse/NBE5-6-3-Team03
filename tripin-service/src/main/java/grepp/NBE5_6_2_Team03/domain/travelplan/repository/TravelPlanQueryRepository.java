package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.persistence.EntityManager;
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
}
