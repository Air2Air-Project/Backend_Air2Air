package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.PollutionIndex;

public interface PollutionIndexRepository extends JpaRepository<PollutionIndex, Long> {

}
