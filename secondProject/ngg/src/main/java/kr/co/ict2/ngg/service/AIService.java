package kr.co.ict2.ngg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict2.ngg.dao.AIDao;
import kr.co.ict2.ngg.vo.AIResVO;

@Service
public class AIService {
    @Autowired
    private AIDao aiDao;

    public int saveTest(AIResVO vo) {
        aiDao.saveTest(vo);
        return vo.getRnum(); // 생성된 rnum 값 반환
    }

    public void updateHistory(AIResVO vo) {
        aiDao.updateHistory(vo);
    }
}
