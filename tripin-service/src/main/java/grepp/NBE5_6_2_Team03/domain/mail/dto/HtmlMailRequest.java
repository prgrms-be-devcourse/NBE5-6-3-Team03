package grepp.NBE5_6_2_Team03.domain.mail.dto;

import java.util.Map;

public record HtmlMailRequest(String to, String subject, String templateName, Map<String,Object> templateModel) {

}
