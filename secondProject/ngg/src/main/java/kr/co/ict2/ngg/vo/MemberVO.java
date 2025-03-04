package kr.co.ict2.ngg.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Getter
@Setter
@Alias("mvo")
public class MemberVO {
	private int mnum;
    private String memail;
	private String mpwd;
	private String mnick;
	private String mname;
	private String mgender;
	private String mbirth;
	private Date mdate;
	private int locnum;
	private String mimgn;
	private String status;
	private String season;
}
