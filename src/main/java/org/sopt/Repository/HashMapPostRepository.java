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
    public void save(Post post){
        if(post.getId() == null){
            post.setId(++sequence);
        }
        posts.put(post.getId(), post);
    }

    @Override
    public List<Post> findAll(){
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> findById(int id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deleteById(int id) {
        posts.remove(id);
    }

}
