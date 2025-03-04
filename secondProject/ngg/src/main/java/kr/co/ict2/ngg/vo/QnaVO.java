package kr.co.ict2.ngg.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("qnavo")
public class QnaVO {

    private int qnum;
    private String mnick;
    private String title;
    private String content;
    private Date cdate;
    private int hit;
    private String ans;
    private Date adate;
  	private int openyn;  
    private int mnum;



}