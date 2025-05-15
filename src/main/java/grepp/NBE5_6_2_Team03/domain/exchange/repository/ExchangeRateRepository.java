package grepp.NBE5_6_2_Team03.domain.exchange.repository;

import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {

    @Query("select e from ExchangeRateEntity e where e.curUnit = :curUnit order by e.date desc limit 1")
    Optional<ExchangeRateEntity> findLatestByCurUnit(String curUnit);

}
