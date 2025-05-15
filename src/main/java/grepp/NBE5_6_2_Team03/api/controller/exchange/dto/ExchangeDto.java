package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import lombok.Data;

@Data
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
        ExchangeDto dto = new ExchangeDto();
        dto.setCurUnit(entity.getCurUnit());
        dto.setCurName(entity.getCurName());
        dto.setBaseRate(entity.getBaseRate());
        dto.setTtbRate(entity.getTtbRate());
        dto.setTtsRate(entity.getTtsRate());
        return dto;
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
