package grepp.NBE5_6_2_Team03.domain.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Table(name = "exchange_rate")
@Getter
@Entity
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

    protected ExchangeRateEntity() {
    }

    @Builder
    private ExchangeRateEntity(String curUnit, String curName, String baseRate,
        String ttbRate,
        String ttsRate, String date) {
        this.curUnit = curUnit;
        this.curName = curName;
        this.baseRate = baseRate;
        this.ttbRate = ttbRate;
        this.ttsRate = ttsRate;
        this.date = date;
    }
}
