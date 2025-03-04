package kr.co.ict2.ngg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict2.ngg.dao.MemberDao;
import kr.co.ict2.ngg.vo.AIResVO;
import kr.co.ict2.ngg.vo.MemberVO;
import java.util.List;

@Service
public class MemberService {
    @Autowired
	private MemberDao memberDao;

	public void memberadd(MemberVO vo) {
		memberDao.memberadd(vo);
	}


	public MemberVO detail(int mnum) {
		return memberDao.mydetail(mnum);
	}
	
	public void myprofileupdate(MemberVO vo) {
		memberDao.myprofileupdate(vo);
	}
	
	public int myidchk(String mnick) {
		return memberDao.myidcheck(mnick);
	}
	
	public void myupdate(MemberVO vo) {
		memberDao.myupdate(vo);
	}

	public void delAccount(int mnum){
		memberDao.delAccount(mnum);
	}


	public List<AIResVO> myhistory(int mnum){
		return memberDao.myhistory(mnum);
    }

	public void delHistory(int rnum){
        memberDao.delHistory(rnum);
    }
	public void seasonupdate(MemberVO vo) {
		memberDao.seasonupdate(vo);
	 }
}
