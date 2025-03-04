package kr.co.ict2.ngg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Random;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.ict2.ngg.dao.CertificationNumberDAO;
import kr.co.ict2.ngg.dao.EmailCertificationDAO;
import kr.co.ict2.ngg.vo.MailCheckVO;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailCertificationDAO emailCertificationDAO;

    @Autowired
    private CertificationNumberDAO certificationNumberDAO;

    // authCode 변수를 삭제합니다. 생성된 인증번호는 메서드 내부에서 관리합니다.

    public int duplicateEmail(String memail) {
        long checkEmail = emailCertificationDAO.countByEmail(memail);
        return checkEmail > 0 ? 1 : 0;
    }

    public String createAuthCode() {
        int length = 6;
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    authCode.append(random.nextInt(10));
                    break;
                case 1:
                    authCode.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    authCode.append((char) (random.nextInt(26) + 97));
                    break;
            }
        }
        return authCode.toString();
    }

    public void sendEmail(String toEmail) {
        String authCode = createAuthCode(); // 로컬 변수로 인증번호를 생성합니다.
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("lkjklll@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("ICTStudy의 X팀의 회원가입 인증번호 발송");

            String body = "<html>" +
                    "<body>" +
                    "<h1>ICTStudy의 X팀의 회원가입을 위한 인증번호</h1>" +
                    "<p>회원가입을 완료하기 위해 아래의 인증코드를 입력해주세요.</p>" +
                    "<p>인증코드: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";

            helper.setText(body, true);
            mailSender.send(message);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);
            System.out.println("인증코드: " + authCode);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        MailCheckVO vo = new MailCheckVO();
        vo.setCode(authCode);
    }

    public boolean isVerify(String memail, String authCode) {
        System.out.println("왜안뜸");

        if (authCode == null) {
            System.out.println("authCode is null");
        } else if (authCode.isEmpty()) {
            System.out.println("authCode is empty");
        } else {
            System.out.println("authCode is not null or empty: " + authCode);
        }

        System.out.println("1:" + certificationNumberDAO.hasKey(memail));
        System.out.println("2:" + authCode);
        System.out.println("3:" + certificationNumberDAO.getCertificationNumber(memail));

        if (certificationNumberDAO.hasKey(memail) && certificationNumberDAO
                .getCertificationNumber(memail).equals(authCode)) {
            certificationNumberDAO.deleteCertificationNumber(memail);
            return true;
        } else {
            return false;
        }
    }
}

