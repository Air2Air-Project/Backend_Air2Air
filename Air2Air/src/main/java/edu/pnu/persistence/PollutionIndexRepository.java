package edu.pnu.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.AirData;
import edu.pnu.domain.PollutionIndex;

public interface PollutionIndexRepository extends JpaRepository<PollutionIndex, Long> {
	@Query("SELECT a FROM PollutionIndex a WHERE FUNCTION('DATE_FORMAT', a.datetime, '%Y-%m-%d %H:%i') = FUNCTION('DATE_FORMAT', :indexDate, '%Y-%m-%d %H:%i')")
	List<PollutionIndex> findByDatetime(Date indexDate);
	
	@Query("SELECT a FROM PollutionIndex a WHERE FUNCTION('DATE_FORMAT', a.datetime, '%Y-%m-%d %H:%i') = FUNCTION('DATE_FORMAT', :indexDate, '%Y-%m-%d %H:%i') AND a.region.regionId = :regionId")
	PollutionIndex findByDatetimeAndRegionRegionId(@Param("indexDate") Date indexDate, @Param("regionId") Long regionId);
}
