package grepp.NBE5_6_2_Team03.domain.admin.place.repository;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, String> {

    Place findByPlaceId(String placeId);

    @Query("select distinct p.country from Place p order by p.country")
    List<String> findDistinctCountries();

    @Query("select distinct p.city from Place p order by p.city")
    List<String> findDistinctCities();
}
