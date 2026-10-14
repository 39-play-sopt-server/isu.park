package org.sopt.app.post.repository;

import org.sopt.app.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class HashMapPostRepository implements PostRepository {
    private final HashMap<Long, Post> posts = new HashMap<>();
    private static Long sequence = 0L; // 게시글 id 자동 증가용 번호

    @Override
    public void save(Post post){   // 게시물 저장
        if(post.getId() == null){
            post.setId(++sequence);
        }
        posts.put(post.getId(), post);
    }

    @Override
    public List<Post> findAll(){   // 전체 게시물 조회
        return new ArrayList<>(posts.values());
    }

    @Override
    public List<Post> findWithPaging(int page, int size){
        List<Post> allposts = new ArrayList<>(posts.values());

        int start = page * size;
        if(start >= allposts.size()){
            return new ArrayList<>(); // 데이터가 없거나, 시작 인덱스가 전체 리스트 크기를 넘어가면 빈 리스트 반환
        }
        int end = Math.min(start + size, allposts.size());
        return allposts.subList(start, end);  // subList로 원하는 범위만큼 잘라 반환
    }

    @Override
    public Optional<Post> findById(Long id) {   // 게시물 단건 조회
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deleteById(Long id) {   // 게시물 삭제
        posts.remove(id);
    }

}
