package grepp.NBE5_6_2_Team03.domain.exchange.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExchangeRateQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QExchangeRateEntity qExchangeRate = QExchangeRateEntity.exchangeRateEntity;
    private final EntityManager em;

    public ExchangeRateQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public int getRecentAverageRate(String countryCode) {
        List<String> baseRates = queryFactory
            .select(qExchangeRate.baseRate)
            .from(qExchangeRate)
            .where(qExchangeRate.curUnit.eq(countryCode))
            .orderBy(qExchangeRate.date.desc())
            .limit(5)
            .fetch();

        return (int) baseRates.stream()
            .map(rate -> rate.replace(",", ""))
            .mapToDouble(Double::parseDouble)
            .average()
            .orElse(0.0);
    }
}
