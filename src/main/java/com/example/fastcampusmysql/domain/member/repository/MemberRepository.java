package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "member";

    static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member //람다식?
            .builder() //매핑 작업
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class)) //클래스 참조. getLocalDate()함수는 없어서 오브젝트로 받음
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    // 회원 정보 조회
    public Optional<Member> findById(Long id){
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);//테이블 이름은 .withTableName("member") 이렇게 계속 써줘야 되는 것이 불편하니까 상단에서 상수로 선언해서 써주기로 한다.
        var params = new MapSqlParameterSource()
                .addValue("id",id); //WHERE id = :id" 이 때 id 값이 여기로 바인딩됨
        //호출하기
        List<Member> members = namedParameterJdbcTemplate.query(sql,params,rowMapper);

        Member nullableMember = DataAccessUtils.singleResult(members);
        return Optional.ofNullable(nullableMember);
    }

    // List In절을 받는 함수
    public List<Member> findAllByIdIn(List<Long> ids){
        if(ids.isEmpty())
            return List.of();

        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)",TABLE);
        var params = new MapSqlParameterSource().addValue("ids",ids);
        return namedParameterJdbcTemplate.query(sql,params,rowMapper);
    } // id로 BeanList가 넘어오면 (:ids) 파싱 실패로 문제가 발생할 수 있음

   //인터페이스 생성
    public Member save(Member member){
        //member id를 보고 갱신 또는 삽입을 정함. 반환값은 id를 담아서 반환함
        if (member.getId() == null){
            return insert(member);
        }
        return update(member);
    }

    //회원 정보 등록
    private Member insert(Member member){ //JDBC 템플릿 이용할 것이라 외부에서 주입 받기
        //id 값을 받아서 반환, SimpleJdbc를 활용해 쉽게 구현 가능함
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
            //테이블 이름 지정
                .withTableName("Member")
                .usingGeneratedKeyColumns("id"); //어떤 컬럼을 키로 가져올 것인지 설정

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member
                .builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member){
        //전체 업데이트로 진행
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id",TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params); //반환값이 Int
        return member;
    }
}
