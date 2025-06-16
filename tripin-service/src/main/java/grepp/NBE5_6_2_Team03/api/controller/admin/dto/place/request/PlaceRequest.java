package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

@Getter
@AllArgsConstructor
public class PlaceRequest {
    @NotBlank(message = "국가는 필수 입력 값입니다.")
    private String country;

    @NotBlank(message = "도시는 필수 입력 값입니다.")
    private String city;

    @NotBlank(message = "장소 이름은 필수 입력 값입니다.")
    private String placeName;

    @NotNull(message = "위도는 필수 입력 값입니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0보다 작을 수 없습니다.")
    @DecimalMax(value = "90.0", message = "위도는 90.0보다 클 수 없습니다.")
    private Double latitude;

    @NotNull(message = "경도는 필수 입력 값입니다.")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0보다 작을 수 없습니다.")
    @DecimalMax(value = "180.0", message = "경도는 180.0보다 클 수 없습니다.")
    private Double longitude;

}