package com.example.fastcampusmysql.domain.util;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {

    static public EasyRandom get(Long memberId, LocalDate fistDate, LocalDate lastDate){
        //데이터 인서트를 위해 아이디값 제외
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));
        //이지랜덤 파라미터 만들기
        var params = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(fistDate,lastDate)
                .randomize(memberIdPredicate,()->memberId);
        return new EasyRandom(params);
    }
}
