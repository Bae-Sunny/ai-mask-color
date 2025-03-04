package kr.co.ict2.ngg.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailCertificationDAO {

    int countByEmail(String memail);

}
