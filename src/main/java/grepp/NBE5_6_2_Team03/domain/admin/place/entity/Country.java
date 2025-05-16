package grepp.NBE5_6_2_Team03.domain.admin.place.entity;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "country")
public class Country extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String countryName;
    private String cityName;
    private Double latitude;
    private Double longitude;
    private int cityRange;

}
