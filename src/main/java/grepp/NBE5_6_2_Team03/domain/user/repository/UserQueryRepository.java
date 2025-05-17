package grepp.NBE5_6_2_Team03.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.QTestQueryDsl;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.TestQueryDsl;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static grepp.NBE5_6_2_Team03.domain.user.QUser.*;

@Repository
public class UserQueryRepository {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public TestQueryDsl queryDslTest(Long userId){
        return queryFactory.select(
                new QTestQueryDsl(user.email, user.name, user.phoneNumber))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }
}
