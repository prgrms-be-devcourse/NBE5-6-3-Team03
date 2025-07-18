package grepp.NBE5_6_2_Team03.domain.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.QTestQueryDsl;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.TestQueryDsl;
import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grepp.NBE5_6_2_Team03.domain.user.QUser.user;
import static grepp.NBE5_6_2_Team03.domain.user.Role.ROLE_USER;

@Repository
public class UserQueryRepository {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public TestQueryDsl queryDslTest(Long userId) {
        return queryFactory.select(
                        new QTestQueryDsl(user.email, user.name, user.phoneNumber))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }

    public Page<User> findUsersPage(String email, Boolean isLocked, Pageable pageable) {

        List<User> users = queryFactory
                .selectFrom(user)
                .where(ContainsEmailAndEqIsLocked(email, isLocked), user.role.eq(ROLE_USER))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(user)
                .where(ContainsEmailAndEqIsLocked(email, isLocked), user.role.eq(ROLE_USER))
                .fetchCount();

        return new PageImpl<>(users, pageable, total);
    }

    public BooleanExpression ContainsEmailAndEqIsLocked(String email, Boolean isLocked) {
        return emailContains(email).and(isLockEq(isLocked));
    }

    public BooleanExpression emailContains(String email) {
        return email != null ? user.email.contains(email) : Expressions.TRUE;
    }

    public BooleanExpression isLockEq(Boolean isLocked) {
        return isLocked != null ? user.isLocked.eq(isLocked) : null;
    }
}
