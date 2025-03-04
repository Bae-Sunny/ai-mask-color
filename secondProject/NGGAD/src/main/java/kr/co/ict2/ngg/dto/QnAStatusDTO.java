package kr.co.ict2.ngg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnAStatusDTO {
    // list에서 받아올 데이터형태
    private Integer qnum;
    private String mnick;
    private String title;
    private Integer hit;

    // '문의 대기' 또는 '문의 완료'
    private String status;

    private Date cdate;

}
