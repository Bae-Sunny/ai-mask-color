package kr.co.ict2.ngg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import kr.co.ict2.ngg.dto.QnACountDTO;

import kr.co.ict2.ngg.table.QnA;

@Transactional
public interface QnARepository extends JpaRepository<QnA, Integer> {

    @Query("SELECT new kr.co.ict2.ngg.dto.QnACountDTO(" +
            "SUM(CASE WHEN q.ans IS NULL THEN 1 ELSE 0 END) as countWithoutAnswer, " +
            "SUM(CASE WHEN q.ans IS NOT NULL THEN 1 ELSE 0 END) as countWithAnswer) " +
            "FROM QnA q")
    QnACountDTO countQuestions();

}
