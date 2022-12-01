package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDTO;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    final private MemberRepository memberRepository;

    public MemberDTO getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow(); // orElseThrow 널 이면 예외 발생 적용
        return toDto(member);
    }

    public MemberDTO toDto(Member member){
        return new MemberDTO(member.getId(),member.getEmail(),member.getNickname(),member.getBirthday());
    }
}
