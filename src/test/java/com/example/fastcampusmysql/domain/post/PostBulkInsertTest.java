package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.domain.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert(){
        var easyRandom = PostFixtureFactory.get(
                3L, //3번의 memberId
                LocalDate.of(2022,1,1),
                LocalDate.of(2022,2,1)
        );

        //IntStream.range 가 얼마나 걸리는지 확인
        var stopWatch = new StopWatch();
        stopWatch.start();


        var posts = IntStream.range(0, 10000 * 100) //생성할 객체 수
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                //.forEach(x -> postRepository.save(x));
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        postRepository.bulkInsert(posts);
        //toList로 바인딩된 리스트를 받아 bulkInsert 를 호출하면 쿼리가 하나로 모여서 나감
    }
}
