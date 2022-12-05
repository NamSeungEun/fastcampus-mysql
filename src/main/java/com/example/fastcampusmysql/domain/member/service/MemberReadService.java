package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDTO;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDTO;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDTO getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow(); // orElseThrow 널 이면 예외 발생 적용
        return toDto(member);
    }

    //findAllByIdIn 구현
    public List<MemberDTO> getMembers(List<Long> ids){
        var members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(this::toDto).toList();
    }

    public List<MemberNicknameHistoryDTO> getNicknameHistories(Long memberId){
        return memberNicknameHistoryRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
        /*
            1. MemberNicknameHistoryRepository 주입 받고
            2. MemberNicknameHistoryDTO를 받는 toDto 생성해주고
            3. List에서 map으로 받아주기
        */
    }

    public MemberDTO toDto(Member member){
        return new MemberDTO(member.getId(),member.getEmail(),member.getNickname(),member.getBirthday());
    }

    public MemberNicknameHistoryDTO toDto(MemberNicknameHistory history){
        return new MemberNicknameHistoryDTO(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }

}
