package grepp.NBE5_6_2_Team03.domain.admin.place;
import grepp.NBE5_6_2_Team03.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "place")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;

}
