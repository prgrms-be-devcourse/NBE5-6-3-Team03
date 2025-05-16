package grepp.NBE5_6_2_Team03.domain.admin.place.repository;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Country;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select c from Country c where c.cityName = :cityName")
    Optional<Country> findByCityName(@Param("cityName") String cityName);

}
