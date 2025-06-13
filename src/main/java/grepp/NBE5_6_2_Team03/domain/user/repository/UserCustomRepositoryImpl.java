package grepp.NBE5_6_2_Team03.domain.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.domain.user.QUser;
import grepp.NBE5_6_2_Team03.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;

    @Override
    public Page<User> findUserWithOption(boolean isLocked, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if(isLocked) {
            builder.and(user.isLocked.eq(true));
        }
        else{
            builder.and(user.isLocked.eq(false));
        }

        List<User> users = jpaQueryFactory
            .selectFrom(user)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = jpaQueryFactory
            .selectFrom(user)
            .where(builder)
            .fetchCount();

        return new PageImpl<>(users, pageable, total);
    }
}
