package grepp.NBE5_6_2_Team03.domain.place.entity;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "place")
@Getter @Setter
@NoArgsConstructor
public class Place extends BaseEntity {

    @Id
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;


    @Builder
    public Place(String country, String city, String placeName, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(String city, String country, String placeName, double latitude, double longitude) {
        this.city = city;
        this.country = country;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
