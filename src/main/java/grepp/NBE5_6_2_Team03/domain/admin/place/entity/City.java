package grepp.NBE5_6_2_Team03.domain.admin.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "city")
public class City {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city_name") // DDL auto 설정 시 없어도 됨
    private String cityName;
    private Double latitude;
    private Double longitude;
    @Column(name = "city_range")
    private int cityRange;

}
