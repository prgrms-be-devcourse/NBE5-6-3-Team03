package grepp.NBE5_6_2_Team03.global.mapper;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;

public class ExchangeMapper {

    public static ExchangeRateEntity toEntity(ExchangeDto dto) {
        return ExchangeRateEntity.builder()
            .curUnit(dto.getCurUnit())
            .curName(dto.getCurName())
            .baseRate(dto.getBaseRate())
            .ttbRate(dto.getTtbRate())
            .ttsRate(dto.getTtsRate())
            .build();
    }

    public static ExchangeDto toDto(ExchangeRateEntity entity) {
        ExchangeDto dto = new ExchangeDto();
        dto.setCurUnit(entity.getCurUnit());
        dto.setCurName(entity.getCurName());
        dto.setBaseRate(entity.getBaseRate());
        dto.setTtbRate(entity.getTtbRate());
        dto.setTtsRate(entity.getTtsRate());
        return dto;
    }

}
