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
@AllArgsConstructor
@Builder
public class PlaceSearchRequest {
    private String country;
    private String city;

    // Pageable 로 묶으면 여기서 Validation 불가능
    @Min(value = 0, message = "페이지는 0 이상이어야 합니다")
    @Max(value = 200, message = "페이지는 최댓값이 200입니다")
    private int page;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    @Max(value = 30, message = "페이지 크기는 30이하여야 합니다")
    private int size;

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}