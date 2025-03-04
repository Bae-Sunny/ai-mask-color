package kr.co.ict2.ngg.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import kr.co.ict2.ngg.dto.MemberDTO;
import kr.co.ict2.ngg.repository.MemberRepository;
import kr.co.ict2.ngg.table.Member;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    public void deleteMember(Long mnum) {
        memberRepository.deleteById(mnum);
    }

    public Member updateMember(MemberDTO dto) {
        Optional<Member> optionalMember = memberRepository.findById(dto.getMnum());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setMemail(dto.getMemail());
            member.setMnick(dto.getMnick());
            member.setMname(dto.getMname());
            member.setMgender(dto.getMgender());
            member.setMbirth(dto.getMbirth());
            member.setMimgn(dto.getMimgn());
            member.setStatus(dto.getStatus());
            member.setSeason(dto.getSeason());
            return memberRepository.save(member);
        }
        return null;
    }

    public List<MemberDTO> getMemList(String searchValue, Integer searchType) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("getMemList", "MemberMapping")
                .registerStoredProcedureParameter("searchValue", String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter("searchType", Integer.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter("mem_cursor", void.class,
                        ParameterMode.REF_CURSOR)
                .setParameter("searchValue", searchValue)
                .setParameter("searchType", searchType);
        query.execute();
        return query.getResultList();
    }

}
