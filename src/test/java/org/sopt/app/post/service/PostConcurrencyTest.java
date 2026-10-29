package org.sopt.app.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.sopt.app.post.entity.Category.FREE;

@SpringBootTest
public class PostConcurrencyTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("동시에 100명이 게시글 조회수를 1씩 증가시킬 때, 조회수가 정확히 100 증가해야 한다.")
    void increaseViewCountConcurrently() throws InterruptedException {
        Post post = new Post(
                "title",
                "content",
                "author",
                LocalDate.now(), // 최초 생성시 생성일 = 수정일
                LocalDate.now(),
                FREE
        );
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // 100개 스레드가 동시에 조회수 증가 요청
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    post.increaseViews();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(); // 모든 스레드의 작업이 끝날 때까지 대기

    }
}
