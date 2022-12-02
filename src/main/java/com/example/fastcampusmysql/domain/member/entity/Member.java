package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    final private Long id; // 변경이 되어서는 안되는 값
    private String nickname; // 변경이 가능한 값
    final private String email;
    final private LocalDate birthday;
    final private LocalDateTime createdAt; //디버깅 용
    final private static Long NAME_MAX_LENGTH = 10L; //닉네임 10글자 제한

    //Member 생성자
    @Builder //롬복의 빌더패턴
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id; //id 값의 유무 확인
        this.birthday = Objects.requireNonNull(birthday); //null이 되면 안되는 값 설정
        this.email = Objects.requireNonNull(email);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);

        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        //널이면 now를 넣어주고 아니면 받은 걸 그대로 넣기
    }
    //닉네임 변경 메소드
    public void changeNickname(String to){
        Objects.requireNonNull(to); //null이 되면 안되는 값 설정
        validateNickname(to); //10자 제한
        nickname = to;
    }

    //NAME 체크 함수
    public void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
