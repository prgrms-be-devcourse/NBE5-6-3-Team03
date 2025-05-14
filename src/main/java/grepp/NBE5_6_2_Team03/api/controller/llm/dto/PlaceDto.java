package grepp.NBE5_6_2_Team03.api.controller.llm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String name;
    private String address;
    private String description;
}