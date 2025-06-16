package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class PlaceSearchRequest {
    private final String country;
    private final String city;

    @Min(value = 0, message = "페이지는 0 이상이어야 합니다")
    private final int page;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    private final int size;

    public PlaceSearchRequest(String country, String city, Integer page, Integer size) {
        this.country = country;
        this.city = city;
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}