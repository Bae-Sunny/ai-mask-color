package kr.co.ict2.ngg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnACountDTO { // Count를 받아 올 DTO

    private Long countWithoutAnswer; // 답변하지 않은 QnA 수
    private Long countWithAnswer; // 답변한 QnA 수
}