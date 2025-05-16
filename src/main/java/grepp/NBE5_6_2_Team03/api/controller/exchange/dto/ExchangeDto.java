package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {

    @JsonProperty("cur_unit")
    private String curUnit;

    @JsonProperty("cur_nm")
    private String curName;

    @JsonProperty("deal_bas_r")
    private String baseRate;

    @JsonProperty("ttb")
    private String ttbRate;     // 외화 -> 원화

    @JsonProperty("tts")
    private String ttsRate;     // 원화 -> 외화

    public static ExchangeDto toDto(ExchangeRateEntity entity) {
        return ExchangeDto.builder()
            .curUnit(entity.getCurUnit())
            .curName(entity.getCurName())
            .baseRate(entity.getBaseRate())
            .ttbRate(entity.getTtbRate())
            .ttsRate(entity.getTtsRate())
            .build();
    }

    public ExchangeRateEntity toEntity(String searchDate) {
        return ExchangeRateEntity.builder()
            .curUnit(this.curUnit)
            .curName(this.curName)
            .baseRate(this.baseRate)
            .ttbRate(this.ttbRate)
            .ttsRate(this.ttsRate)
            .date(searchDate)
            .build();
    }

}
