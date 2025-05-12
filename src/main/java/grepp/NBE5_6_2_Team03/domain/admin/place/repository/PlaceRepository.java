package grepp.NBE5_6_2_Team03.domain.admin.place.repository;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
