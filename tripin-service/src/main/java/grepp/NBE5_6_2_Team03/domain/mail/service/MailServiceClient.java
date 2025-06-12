package grepp.NBE5_6_2_Team03.domain.mail.service;

import grepp.NBE5_6_2_Team03.domain.mail.dto.CodeMailRequest;
import grepp.NBE5_6_2_Team03.domain.mail.dto.CodeMailResponse;
import grepp.NBE5_6_2_Team03.domain.mail.dto.CodeType;
import grepp.NBE5_6_2_Team03.domain.mail.dto.HtmlMailRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailServiceClient {

    private final WebClient webClient;

    @Value("${mail-service.url}")
    private String mailServiceUrl;

    public Mono<CodeMailResponse> sendCode(String to, CodeType codeType) {
        CodeMailRequest request = new CodeMailRequest(to, codeType);
        return webClient.post()
            .uri(mailServiceUrl + "/api/mail/send-code")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(CodeMailResponse.class)
            .doOnError(error -> log.error("코드 메일 요청 실패", error));
    }

    public Mono<Void> sendHtml(String to, String subject, String templateName, Map<String, Object> model) {
        HtmlMailRequest request = new HtmlMailRequest(to, subject, templateName, model);
        return webClient.post()
            .uri(mailServiceUrl + "/api/mail/send-html")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .doOnError(error -> log.error("HTML 메일 요청 실패", error));
    }
}
