package kr.co.ict2.ngg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import kr.co.ict2.ngg.dto.MemberDTO;
import kr.co.ict2.ngg.service.MemberService;
import kr.co.ict2.ngg.table.Member;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/list") // 회원 리스트
    public List<MemberDTO> list(@RequestParam Map<String, String> paramMap) {
        System.out.println("searchType:" + paramMap.get("searchType"));
        Integer searchType = Integer.parseInt(paramMap.get("searchType"));
        System.out.println("searchValue:" + paramMap.get("searchValue"));
        String searchValue = paramMap.get("searchValue");
        return memberService.getMemList(searchValue, searchType);
    }

    @PostMapping("/update") // 회원 수정
    public Member updateMember(@RequestBody MemberDTO memberDTO) {
        System.out.println("신호확인");
        return memberService.updateMember(memberDTO);
    }

    @DeleteMapping("/delete/{mnum}") // 회원 상세보기
    public void deleteMember(@PathVariable("mnum") Long mnum) {
        System.out.println(mnum);
        memberService.deleteMember(mnum);
    }

}
