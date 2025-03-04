package kr.co.ict2.ngg.vo;

import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("aivo")
public class AIResVO {

    private int rnum;
    private int mnum;
    private String befimgn;
    private String afimgn;
    private String rdate;
    private String season;

}
