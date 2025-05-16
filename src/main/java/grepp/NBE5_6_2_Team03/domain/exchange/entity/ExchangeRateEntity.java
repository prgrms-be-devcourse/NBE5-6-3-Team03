package grepp.NBE5_6_2_Team03.domain.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Table(name = "exchange_rate")
@Getter @Entity
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

    public ExchangeRateEntity() {

    }

    public ExchangeRateEntity(Long id, String curUnit, String curName, String baseRate,
        String ttbRate,
        String ttsRate, String date) {
        this.id = id;
        this.curUnit = curUnit;
        this.curName = curName;
        this.baseRate = baseRate;
        this.ttbRate = ttbRate;
        this.ttsRate = ttsRate;
        this.date = date;
    }

}
