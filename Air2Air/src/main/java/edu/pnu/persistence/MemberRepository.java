package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
	Optional<Member> findByUsername(String name);
	Optional<Member> findByPhoneNumber(String phoneNumber);
	Optional<Member> findByEmailAndPhoneNumber(String Email, String phoneNumber);
}
