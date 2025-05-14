package grepp.NBE5_6_2_Team03.domain.admin.place.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "place")
@Getter @Setter
@NoArgsConstructor
public class Place {

    @Id
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;


    public Place(String country, String city, String placeName, String address, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
