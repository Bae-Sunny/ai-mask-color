package kr.co.ict2.ngg.table;

//import java.time.LocalDate;

import jakarta.persistence.*;
import kr.co.ict2.ngg.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MEMBER")
@SqlResultSetMapping(name = "MemberMapping", classes = @ConstructorResult(targetClass = MemberDTO.class, columns = {
        @ColumnResult(name = "mnum", type = Long.class),
        @ColumnResult(name = "memail", type = String.class),
        @ColumnResult(name = "mname", type = String.class),
        @ColumnResult(name = "mnick", type = String.class),
        @ColumnResult(name = "mgender", type = String.class),
        @ColumnResult(name = "mbirth", type = String.class),
        @ColumnResult(name = "season", type = String.class),
        @ColumnResult(name = "mimgn", type = String.class),
        @ColumnResult(name = "status", type = String.class),
        @ColumnResult(name = "mdate", type = Date.class)
}))
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    @SequenceGenerator(name = "seq_member", sequenceName = "seq_member", allocationSize = 1)
    private Long mnum; // 회원번호

    @Column(length = 200, nullable = false, unique = true)
    private String memail; // 이메일(id)

    @Column(length = 200, nullable = false)
    private String mpwd; // 비밀번호

    @Column(length = 200, nullable = true)
    private String mimgn; // 사진이름

    @Column(length = 200, nullable = false)
    private String mnick; // 닉네임

    @Column(length = 200, nullable = false, unique = true)
    private String mname; // 이름

    @Column(nullable = false, length = 50)
    private String mgender; // 성별

    @Column(length = 200, nullable = false)
    private String mbirth; // 생년월일

    // @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdate; // 가입날짜

    @Column(nullable = false)
    private int locnum; // 지역

    @Column(nullable = true, length = 200)
    private String season; // 퍼스널컬러

    @Column(length = 50, nullable = true)
    @ColumnDefault("active")
    private String status; // 회원상태

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QnA> qnaList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AI_RES> aiResList = new ArrayList<>();

}
