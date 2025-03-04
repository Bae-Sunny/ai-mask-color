package kr.co.ict2.ngg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict2.ngg.dao.QnaDao;
import kr.co.ict2.ngg.vo.QnaVO;

@Service
public class QnaService {

	@Autowired
	private QnaDao qnaDao;

	public void qnaWrite(QnaVO vo) {
		qnaDao.qnawrite(vo);
	}

	public List<QnaVO> qnaList(Map<String, String> map) {
		return qnaDao.qnalist(map);
	}

	public QnaVO qnaDetail(int qnum) {
		qnaDao.qnahit(qnum);
		return qnaDao.qnadetail(qnum);
	}

	public void qnaUpdate(QnaVO vo) {
		qnaDao.qnaupdate(vo);
	}

	public void qnaDelete(int qnum) {
		qnaDao.qnadelete(qnum);
	}

}
