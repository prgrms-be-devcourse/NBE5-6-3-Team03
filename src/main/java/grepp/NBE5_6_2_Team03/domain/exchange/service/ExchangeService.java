package grepp.NBE5_6_2_Team03.domain.exchange.service;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeDto[] getCurrentExchanges() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String url = String.format("%s?authkey=%s&searchdate=%s&data=AP01",apiUrl,apiKey,today);

        ResponseEntity<ExchangeDto[]> exchangeResponse = restTemplate.getForEntity(url, ExchangeDto[].class);

        return exchangeResponse.getBody();
    }
}