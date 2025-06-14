package grepp.NBE5_6_2_Team03.domain.exchange.repository;

import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {

    Optional<ExchangeRateEntity> findTop1ByCurUnitOrderByDateDesc(String curUnit);
}
