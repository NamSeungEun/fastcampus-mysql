
package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDTO;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController {
    final private MemberWriteService memberWriteService; //의존성 주입에 따른 어노테이션 추가
    final private MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDTO register(@RequestBody RegisterMemberCommand command){
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);

    }

    @GetMapping("/members/{id}")
    public MemberDTO getMember(@PathVariable Long id){
        return memberReadService.getMember(id);
    }

}