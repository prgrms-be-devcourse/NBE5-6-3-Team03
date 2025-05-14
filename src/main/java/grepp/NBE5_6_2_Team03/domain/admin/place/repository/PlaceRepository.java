package grepp.NBE5_6_2_Team03.domain.admin.place.repository;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {
    public Place findByPlaceId(String placeId);
}
