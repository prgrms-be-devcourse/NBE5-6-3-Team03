package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place;

import lombok.Getter;

@Getter
public class PlaceSearchRequest {
    private String country;
    private String city;
    private String nameKeyword;
    private int page = 0;
    private int size = 10;
}
