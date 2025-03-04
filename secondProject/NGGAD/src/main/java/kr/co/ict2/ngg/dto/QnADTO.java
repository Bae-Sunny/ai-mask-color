package kr.co.ict2.ngg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QnADTO {

    private Integer qnum;
    private String mnick;
    private String title;
    private String content;
    private Date cdate;
    private Integer hit;
    private String ans;
    private Date adate;
    private String openyn;
    private Integer mnum;
}
