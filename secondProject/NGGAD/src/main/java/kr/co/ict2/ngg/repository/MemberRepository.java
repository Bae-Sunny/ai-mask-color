package kr.co.ict2.ngg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import kr.co.ict2.ngg.table.Member;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

}
