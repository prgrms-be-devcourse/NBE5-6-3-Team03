package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class PlaceSearchRequest {
    private String country;
    private String city;

    private String keyword;

    @Min(value = 0, message = "페이지는 0 이상이어야 합니다")
    @Max(value = 200, message = "페이지는 최댓값이 200입니다")
    private Integer page;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    @Max(value = 30, message = "페이지 크기는 30이하여야 합니다")
    private Integer size;

    public PlaceSearchRequest(String country, String city, String keyword,Integer page, Integer size) {
        this.country = (country == null) ? "" : country;
        this.city = (city == null) ? "" : city;
        this.keyword = (keyword == null) ? "" : keyword;
        this.page = (page == null) ? 0 : page;
        this.size = (size == null) ? 30 : size;
    }

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}