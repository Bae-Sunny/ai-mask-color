package kr.co.ict2.ngg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict2.ngg.service.EmailSenderService;
import kr.co.ict2.ngg.vo.MailCheckVO;

@RestController
@RequestMapping("/api/auth")
public class MailCertificationController {

    @Autowired
    private EmailSenderService emailSenderService;

    // 이메일 중복 확인
    @PostMapping("/emailCheck")
    public int sendEmail(@RequestBody MailCheckVO memail) {
        System.out.println("요청 처리됨" + memail.getMemail());
        int checkEmail = emailSenderService.duplicateEmail(memail.getMemail());
        if (checkEmail == 0) {
            emailSenderService.sendEmail(memail.getMemail());
            return 0;
        } else {
            return 1;
        }
    }

    // 이메일 인증번호 확인
    @PostMapping("/emailCheck/certification")
    public boolean verifyCertificationNumber(@RequestBody MailCheckVO vo) {
        System.out.println(vo.getMemail() + ":" + vo.getCode());
        // System.out.println("안나와.."+emailSenderService.isVerify(vo.getMemail(), vo.getCode()));
        return emailSenderService.isVerify(vo.getMemail(), vo.getCode());
    }
}

