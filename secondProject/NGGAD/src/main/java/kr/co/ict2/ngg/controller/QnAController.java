package kr.co.ict2.ngg.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict2.ngg.dto.QnACountDTO;
import kr.co.ict2.ngg.dto.QnADTO;
import kr.co.ict2.ngg.dto.QnAStatusDTO;
import kr.co.ict2.ngg.service.QnAService;
import kr.co.ict2.ngg.table.QnA;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/qna")
public class QnAController {
    @Autowired
    QnAService qnAService;

    @PostMapping("/detail/{qnum}") // 상세보기
    public QnADTO getMethodName(@PathVariable("qnum") Integer qnum) {
        System.out.println(qnum);
        return qnAService.findbyqnum(qnum);
    }

    @PostMapping("/updateAnsAndAdate") // QnA 답변
    public void updateAnsAndAdate(@RequestParam("qnum") Integer qnum, @RequestParam("ans") String ans) {
        qnAService.updateAnsAndAdate(qnum, ans);

    }

    @PostMapping("/qnalist") // QnA 리스트
    public List<QnAStatusDTO> getQnAlist1(@RequestParam Map<String, String> paramMap) {
        Integer searchType = Integer.parseInt(paramMap.get("searchType"));
        String searchValue = paramMap.get("searchValue");
        return qnAService.getQnAReport(searchValue, searchType);
    }

    @GetMapping("/countAns") // QnA 카운트
    public QnACountDTO questionStatistics() {
        return qnAService.getQuestionStatistics();
    }
}