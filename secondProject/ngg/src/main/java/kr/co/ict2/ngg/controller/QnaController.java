package kr.co.ict2.ngg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kr.co.ict2.ngg.service.QnaService;
import kr.co.ict2.ngg.vo.QnaVO;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @RequestMapping("/qnaWrite")
    public ResponseEntity<?> qnaWrite(QnaVO vo) {
        qnaService.qnaWrite(vo);
        return ResponseEntity.ok().body(1);
    }

    @RequestMapping("/qnaList")
    public List<QnaVO> qnaList(QnaVO vo, Model model,
            @RequestParam Map<String, String> paramMap) {
        System.out.println("searchType:" + paramMap.get("searchType"));
        System.out.println("searchValue:" + paramMap.get("searchValue"));
        System.out.println("searchValue:" + paramMap.get("cPage"));
        return qnaService.qnaList(paramMap);
    }

    @RequestMapping("/qnaDetail")
    public QnaVO qnaDetail(@RequestParam("qnum") int qnum) {
        return qnaService.qnaDetail(qnum);
    }

    @RequestMapping("/qnaUpdate")
    public ResponseEntity<String> qnaUpdate(@RequestBody QnaVO qnaVO) {
        System.out.println("in qnaUpdate getQnum: " + qnaVO.getQnum());
        qnaService.qnaUpdate(qnaVO);
        return ResponseEntity.ok("QNA 수정완료");
    }

    @RequestMapping("/qnaDelete")
    public ResponseEntity<String> qnaDelete(@RequestParam("qnum") int qnum) {
        System.out.println("in qnaDelete:" + qnum);
        qnaService.qnaDelete(qnum);
        return ResponseEntity.ok("QNA 삭제완료");
    }

}
