package kr.co.ict2.ngg.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ict2.ngg.service.MemberService;
import kr.co.ict2.ngg.vo.AIResVO;
import kr.co.ict2.ngg.vo.MemberVO;
import java.util.List;


@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<?> addMember(@RequestBody MemberVO vo) {
		System.out.println("....................................");
		memberService.memberadd(vo);
		return ResponseEntity.ok().body(vo);
	}

   private final static String filePath = Paths.get("").toAbsolutePath()+"/front/public/img/upimg";
   
   public MemberController(){
		System.out.println("imagePath=>" + filePath);

		Path directoryPath = Paths.get(filePath);
	   
		if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
                System.out.println("디렉토리 생성: " + directoryPath);
            } catch (Exception e) {
                System.err.println("디렉토리 생성 실패: " + e.getMessage());
            }
        }
   }
	
	
	@GetMapping("/detail")
	public MemberVO detail(@RequestParam("mnum") int num) {
		return memberService.detail(num);
	}
	
	
	@PostMapping("/myprofileupdate")
	public ResponseEntity<?> myprofileupdate(@RequestParam("file") MultipartFile mf, 
											 MemberVO vo, @RequestParam("mnum") int mnum, 
											 HttpServletRequest request) {
		if (mf == null || mf.isEmpty()) {
			throw new IllegalArgumentException("파일이 전송되지 않았습니다.");
		}
		String oriFn = mf.getOriginalFilename();
		StringBuffer path = new StringBuffer();
		path.append(filePath).append("\\");
		path.append(oriFn);
		System.out.println("FullPath :" + path);
		File f = new File(path.toString());
		try {
			mf.transferTo(f);
			vo.setMimgn(oriFn);
			memberService.myprofileupdate(vo);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(1);
	}
	
	
	
	@GetMapping("/myidchk/{mnick}")
	public int myidchk(@PathVariable("mnick") String mnick) {
		System.out.println(mnick);
		return memberService.myidchk(mnick);
	}

	
	@PostMapping("/myupdate")
	public void myupdate(MemberVO vo) {
		memberService.myupdate(vo);
	}


	 @GetMapping("/myhistory")
		public List<AIResVO> myhistory(@RequestParam("mnum") int mnum) {
		System.out.println("controller: " + mnum);
		return memberService.myhistory(mnum);
	}

	@GetMapping("/delHistory")
	public void delHistory(@RequestParam int rnum) {
	   System.out.println("삭제한 게시물 번호"+rnum);
	   memberService.delHistory(rnum);
	}

	@GetMapping("/delAccount")
	public void delAccount(@RequestParam("mnum") int mnum){
		System.out.println("탈퇴한 회원 번호 : "+mnum);
		memberService.delAccount(mnum);
	}
	@PostMapping("/seasonupdate")
	public void seasonupdate(@RequestBody MemberVO vo) {
	   memberService.seasonupdate(vo);
	}

	
}