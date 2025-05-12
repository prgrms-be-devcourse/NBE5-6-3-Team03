package grepp.NBE5_6_2_Team03.domain.admin.place.entity;
import grepp.NBE5_6_2_Team03.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
public class Place extends BaseEntity {

    @Id
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private String address;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


    public Place(String country, String city, String placeName, String address) {
        this.country = country;
        this.city = city;
        this.placeName = placeName;
        this.address = address;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();  // 객체가 처음 저장될 때 생성 시간을 설정
        updatedAt = LocalDateTime.now();  // 처음 저장될 때 업데이트 시간도 설정
    }
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();  // 업데이트 시 업데이트 시간을 설정
    }
}
