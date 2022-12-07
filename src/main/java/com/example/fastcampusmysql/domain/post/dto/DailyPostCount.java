package com.example.fastcampusmysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCount(Long memberId, LocalDate date, Long postCount) {
    // 회원 아이디, 며칠 자인지, 몇 개의 게시물을 썼는지
}
