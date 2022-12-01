package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand(
        // 레코드로 선언 시 getter setter와 getter가 프로퍼티 형색으로 자동 생성됨
        String email,
        String nickname,
        LocalDate birthday
) {
}
