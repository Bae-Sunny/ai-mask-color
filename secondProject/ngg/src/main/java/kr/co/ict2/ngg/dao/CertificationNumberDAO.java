package kr.co.ict2.ngg.dao;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CertificationNumberDAO {
    private final StringRedisTemplate stringRedisTemplate;

    public void saveCertificationNumber(String memail, String authCode) {
        stringRedisTemplate.opsForValue().set(memail, authCode, Duration.ofSeconds(10000));
    }
     // 이메일 인증번호 가져오기
     public String getCertificationNumber(String memail) {
        System.out.println("생성된 인증키:"+stringRedisTemplate.opsForValue().get(memail));
        return stringRedisTemplate.opsForValue().get(memail);
    }

    // 이메일 인증번호 삭제
    public void deleteCertificationNumber(String memail) {
        stringRedisTemplate.delete(memail);
    }

    // 이메일 인증번호 존재 여부
    public boolean hasKey(String memail) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(memail));
    }
}
