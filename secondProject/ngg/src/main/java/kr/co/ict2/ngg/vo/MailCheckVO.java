package kr.co.ict2.ngg.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("cvo")
public class MailCheckVO {
    private String memail;
    private String code;
}
