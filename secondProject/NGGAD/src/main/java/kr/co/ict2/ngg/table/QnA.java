package kr.co.ict2.ngg.table;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import kr.co.ict2.ngg.dto.QnAStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SqlResultSetMapping(name = "QnAStatusDTOMapping", classes = { // 검색 프로시저
                @ConstructorResult(targetClass = QnAStatusDTO.class, columns = {
                                @ColumnResult(name = "qnum", type = Integer.class),
                                @ColumnResult(name = "mnick", type = String.class),
                                @ColumnResult(name = "title", type = String.class),
                                @ColumnResult(name = "hit", type = Integer.class),
                                @ColumnResult(name = "status", type = String.class),
                                @ColumnResult(name = "cdate", type = Date.class)
                }) })
public class QnA {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_qna")
        @SequenceGenerator(name = "seq_qna", sequenceName = "seq_qna", allocationSize = 1)
        private Integer qnum; // QnA번호

        @Column(length = 200, nullable = false)
        private String mnick; // 닉네임

        @Column(length = 200, nullable = false)
        private String title; // 주제

        @Lob
        @Column(nullable = false)
        private String content; // 내용

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date cdate; // 작성 날짜

        @Column(length = 10)
        @ColumnDefault("0")
        private Integer hit; // 조회수

        @Lob
        @Column
        private String ans; // 답변

        @Column
        @Temporal(TemporalType.TIMESTAMP)
        private Date adate; // 답변 작성날짜

        @Column(length = 10, nullable = false)
        private Integer openyn; // 공개 여부

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "mnum")
        private Member member;

}
