package grepp.NBE5_6_2_Team03.api.controller.exchange.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class ExchangeListResponse {

    private final Map<String, List<ExchangeDetailResponse>> exchangeListResponse;

    public ExchangeListResponse(Map<String, List<ExchangeDetailResponse>> exchangeListResponse) {
        this.exchangeListResponse = exchangeListResponse;
    }

    public static ExchangeListResponse from(List<ExchangeResponse> given) {
        List<ExchangeDetailResponse> korList = new ArrayList<>();
        List<ExchangeDetailResponse> jpList = new ArrayList<>();
        List<ExchangeDetailResponse> thList = new ArrayList<>();

        for (ExchangeResponse exchange : given) {
            switch (exchange.getCurUnit()) {
                case "JPY(100)" -> {
                    jpList.add(buildDetail(exchange));
                }

                case "KRW" -> {
                    korList.add(buildDetail(exchange));
                }

                case "THB" -> {
                    thList.add(buildDetail(exchange));
                }

            }
        }

        Map<String, List<ExchangeDetailResponse>> map = new HashMap<>();
        map.put("JPY", jpList);
        map.put("KRW", korList);
        map.put("THB", thList);

        return new ExchangeListResponse(map);
    }

    private static ExchangeDetailResponse buildDetail(ExchangeResponse exchange) {
        return
            ExchangeDetailResponse.builder()
            .baseRate(exchange.getBaseRate())
            .ttbRate(exchange.getTtbRate())
            .ttsRate(exchange.getTtsRate())
            .build();
    }
}
