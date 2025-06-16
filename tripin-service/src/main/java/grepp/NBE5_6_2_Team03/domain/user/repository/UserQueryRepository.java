package grepp.NBE5_6_2_Team03.domain.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.QTestQueryDsl;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.TestQueryDsl;
import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<User> findUsersPage(String email, Boolean isLocked, Pageable pageable) {

        BooleanBuilder searchTerms = new BooleanBuilder();

        searchTerms.and(user.role.eq(Role.ROLE_USER));
        if (email != null && !email.isEmpty()) {
            searchTerms.and(user.email.contains(email));
        }
        if (isLocked != null) {
            searchTerms.and(user.isLocked.eq(isLocked));
        }


        List<User> users = queryFactory
            .selectFrom(user)
            .where(searchTerms)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory
            .selectFrom(user)
            .where(searchTerms)
            .fetchCount();

        return new PageImpl<>(users, pageable, total);
    }

}
