package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {
    final private MemberReadService memberReadService;
    final private FollowWriteService followWriteService; //의존성이 잘 분리되어 사용됨

    public void excute(Long fromMemberId, Long toMemberId) {
        /* 1. 입력 받은 memberId로 회원 조회
        *  2. FollowWriteService.create() 호출 */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember,toMember);
    }
}
