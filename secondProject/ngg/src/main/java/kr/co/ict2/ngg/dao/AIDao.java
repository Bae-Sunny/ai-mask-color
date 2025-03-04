package kr.co.ict2.ngg.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict2.ngg.vo.AIResVO;

@Mapper
public interface AIDao {

    public void saveTest(AIResVO vo);

    public void updateHistory(AIResVO vo);

}
