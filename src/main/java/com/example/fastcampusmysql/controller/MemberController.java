
package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDTO;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDTO;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    final private MemberWriteService memberWriteService; //의존성 주입에 따른 어노테이션 추가
    final private MemberReadService memberReadService;

    @PostMapping
    public MemberDTO register(@RequestBody RegisterMemberCommand command){
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{id}")
    public MemberDTO getMember(@PathVariable Long id){
        return memberReadService.getMember(id);
    }

    //회원 정보 변경
    @PostMapping("/{id}/name")
    public MemberDTO changeNickname(@PathVariable Long id, @RequestBody String nickname){
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }
    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDTO> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }
}