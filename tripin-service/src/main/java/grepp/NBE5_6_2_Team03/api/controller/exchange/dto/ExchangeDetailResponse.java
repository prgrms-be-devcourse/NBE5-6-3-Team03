package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExchangeDetailResponse {

    @JsonProperty("deal_bas_r")
    private String baseRate;

    @JsonProperty("ttb")
    private String ttbRate;     // 외화 -> 원화

    @JsonProperty("tts")
    private String ttsRate;     // 원화 -> 외화

    @Builder
    public ExchangeDetailResponse(String baseRate, String ttbRate, String ttsRate) {
        this.baseRate = baseRate;
        this.ttbRate = ttbRate;
        this.ttsRate = ttsRate;
    }
}
