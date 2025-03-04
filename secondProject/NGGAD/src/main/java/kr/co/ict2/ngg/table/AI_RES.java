package kr.co.ict2.ngg.table;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AI_RES")
public class AI_RES {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ai_res")
    @SequenceGenerator(name = "seq_ai_res", sequenceName = "seq_ai_res", allocationSize = 1)
    private Integer rnum; // AI결과 번호

    @Column(nullable = false, length = 200)
    private String befimgn; // 이전 이미지 이름

    @Column(nullable = false, length = 200)
    private String afimgn; // MakeUp 이미지 이름

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rdate; // 진단 날짜

    @Column(length = 150)
    private String season; // 톤 이름

    @JoinColumn(name = "mnum")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;
}
