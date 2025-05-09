package grepp.NBE5_6_2_Team03.domain.admin.place.parser;

import com.fasterxml.jackson.databind.JsonNode;

public class JapanAddressParser implements AddressParser {

    @Override
    public String parsingCountry(JsonNode addressComponents) {
        return extractByType(addressComponents, "country");
    }

    @Override
    public String parsingCity(JsonNode addressComponents) {
        return extractByType(addressComponents, "administrative_area_level_1");
    }

    @Override
    public String extractByType(JsonNode components, String targetType) {
        for (JsonNode component : components) {
            for (JsonNode typeNode : component.path("types")) {
                if (typeNode.asText().equals(targetType)) {
                    return component.path("long_name").asText();
                }
            }
        }
        return null;
    }
}

