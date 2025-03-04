package kr.co.ict2.ngg.dto;

import java.util.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberDTO {
    private Long mnum;
    private String memail;
    private String mpwd;
    private String mnick;
    private String mname;
    private String mgender;
    private String mbirth;
    private Date mdate;
    private int locnum;
    private String mimgn;
    private String season;
    private String status;

}
