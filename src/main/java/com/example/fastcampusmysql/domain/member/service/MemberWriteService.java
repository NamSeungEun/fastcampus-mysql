package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    final private MemberRepository memberRepository; //의존성 주입을 위해 RequiredArgsConstructor 어노테이션이 필요함

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {

/*      요구사항 - 식별 가능한 회원정보(이메일,닉넴,생년월일) 등록
                - 닉넴은 10자를 넘길 수 없다.
        파라미터로 받을 memberRegisterCommond / dto에서 만듦
        회원 개체 생성 val member = Member.of(memberRegisterCommond)
        memberRepository.save(member)
*/
        var member = Member
                .builder() //entity/Member.java/lombok의 @Builder
                .nickname(command.nickname()) //ID는 왜 빌더 패턴에서 빠졌지?
                .birthday(command.birthday())
                .email(command.email())
                .build();

        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }
    //회원 이름 변경 히스토리 저장하기
    public void changeNickname(Long memberId,String nickname){
        var member = memberRepository.findById(memberId).orElseThrow(); //닉넴 없으면 터트리기
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }
    private void saveMemberNicknameHistory(Member member){
        var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

}
