package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName(){
        var member = MemberFixtureFactory.create();
        var expected = "nams";

        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    //validateNickname 걸리는 부분 테스트 코드 작성
    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다")
    @Test
    public void testNicknameMaxLength(){
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "namnamnamnams"; //Assertions이 터지게끔 유도

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthName)
        );
    }
}
