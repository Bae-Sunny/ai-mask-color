package kr.co.ict2.ngg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict2.ngg.vo.QnaVO;

@Mapper
public interface QnaDao {

    public void qnawrite(QnaVO vo); // 게시판 글 작성

    public List<QnaVO> qnalist(Map<String, String> map); // 게시판 리스트

    public QnaVO qnadetail(int qnum); // 게시판 상세정보

    public void qnahit(int qnum); // 게시판 조회수

    public void qnaupdate(QnaVO vo); // 게시판 업데이트

    public void qnadelete(int qnum); // 게시판 삭제

}
