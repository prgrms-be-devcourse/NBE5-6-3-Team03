package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

}
