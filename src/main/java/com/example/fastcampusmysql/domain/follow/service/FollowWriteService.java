package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
@RequiredArgsConstructor
@Service
public class FollowWriteService {

    final private FollowRepository followRepository;

    //팔로우 객체 생성
    public void create(MemberDTO fromMember, MemberDTO toMember){
        /*
           from, to의 회원 정보 받고 저장하기
           from - to 가 같을 수는 없으니 validate 추가
         */

        Assert.isTrue(!fromMember.id().equals(toMember.id()),"From, To 회원이 동일합니다.");

        var follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();

        followRepository.save(follow);
    }
}
