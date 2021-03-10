package com.dev.availabilitytracker.Repository;

import com.dev.availabilitytracker.model.Rickshaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RickshawRepository extends JpaRepository<Rickshaw, Long> {

    @Query(
            value = "select * from rickshaw where calculate_distance(lat, lng, ?1, ?2, 'm') <=  ?3",
            nativeQuery = true
    )
    List<Rickshaw> findWithinRange(double lat, double lng, Long distance);
}
