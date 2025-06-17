package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ExchangeListResponse {
    private List<ExchangeResponse> exchanges;

    public ExchangeListResponse(List<ExchangeResponse> exchanges) {
        this.exchanges = exchanges;
    }
}
