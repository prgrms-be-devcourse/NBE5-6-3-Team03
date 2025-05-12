package grepp.NBE5_6_2_Team03.domain.admin.place.repository;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository {

    @Query(value = "select c from city c where c.city_name = :cityName")
    Optional<City> findByCityName(@Param("cityName") String cityName);

}
