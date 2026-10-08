package org.sopt.Repository;

import org.sopt.Model.Post;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HashMapPostRepository implements PostRepository {
    private final HashMap<Integer, Post> posts = new HashMap<>();
    private static int sequence = 0; // 게시글 id 자동 증가용 번호

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
    public Optional<Post> findById(int id) {   // 게시물 단건 조회
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deleteById(int id) {   // 게시물 삭제
        posts.remove(id);
    }

}
