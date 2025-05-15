package grepp.NBE5_6_2_Team03.domain.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "exchange_rate")
@Getter @Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String curUnit;
    private String curName;
    private String baseRate;
    private String ttbRate;
    private String ttsRate;
    private String date;

}
