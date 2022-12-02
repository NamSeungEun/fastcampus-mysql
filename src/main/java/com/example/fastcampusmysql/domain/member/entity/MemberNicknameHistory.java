package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
@Getter
public class MemberNicknameHistory {
    final private Long id; //식별자

    final private Long memberId;

    final private String nickname; //히스토리성 데이터는 정규화의 대상이 아님!! 정규화 시 필히 고려해야 함

    final private LocalDateTime createdAt;

    //생성자
    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt; // Member 생성자에서 진행했듯이 베이직 문장
    }
}
