package edu.pnu.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.AirData;

public interface AirDataRepository extends JpaRepository<AirData, Long> {
	@Query("SELECT a FROM AirData a WHERE FUNCTION('DATE_FORMAT', a.datetime, '%Y-%m-%d %H:%i') = FUNCTION('DATE_FORMAT', :indexDate, '%Y-%m-%d %H:%i')")
    List<AirData> findByDateTime(@Param("indexDate") Date indexDate);
}
