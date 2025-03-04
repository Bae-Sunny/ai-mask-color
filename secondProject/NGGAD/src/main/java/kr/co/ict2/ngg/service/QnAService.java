package kr.co.ict2.ngg.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import kr.co.ict2.ngg.dto.QnACountDTO;
import kr.co.ict2.ngg.dto.QnADTO;
import kr.co.ict2.ngg.dto.QnAStatusDTO;
import kr.co.ict2.ngg.repository.QnARepository;
import kr.co.ict2.ngg.table.QnA;

@Service
@Transactional
public class QnAService {
    @Autowired
    private QnARepository qnARepository;

    @Autowired
    private EntityManager entityManager;

    public QnADTO findbyqnum(Integer qnum) { // 상세보기
        QnA qna = qnARepository.findById(qnum).orElse(null);
        QnADTO dto = new QnADTO();
        dto.setQnum(qna.getQnum());
        dto.setMnick(qna.getMnick());
        dto.setTitle(qna.getTitle());
        dto.setContent(qna.getContent());
        dto.setCdate(qna.getCdate());
        dto.setHit(qna.getHit());
        dto.setAns(qna.getAns());
        dto.setAdate(qna.getAdate());
        return dto;
    }

    public void updateAnsAndAdate(Integer qnum, String ans) { // 답변 작성 업데이트
        QnA qna = qnARepository.findById(qnum)
                .orElseThrow(() -> new RuntimeException("Qna with qnum " + qnum + " not found"));
        qna.setAns(ans);
        qna.setAdate(new Date()); // 현재 날짜와 시간을 설정
        qnARepository.save(qna);
    }

    public QnACountDTO getQuestionStatistics() {
        return qnARepository.countQuestions();
    }

    public List<QnAStatusDTO> getQnAReport(String searchValue, Integer searchType) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("GetQnAReport", "QnAStatusDTOMapping")
                .registerStoredProcedureParameter("searchValue", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("searchType", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("qna_cursor", void.class, ParameterMode.REF_CURSOR)
                .setParameter("searchValue", searchValue)
                .setParameter("searchType", searchType);
        query.execute();
        return query.getResultList();
    }

}
