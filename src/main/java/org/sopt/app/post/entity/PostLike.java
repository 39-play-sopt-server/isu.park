package org.sopt.app.post.entity;

import jakarta.persistence.*;
import org.sopt.app.member.entity.Member;

@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) // 게시글과 관계
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY) // 유저와 관계
    @JoinColumn(name = "member_id")
    private Member member;

    protected PostLike() {}

    public PostLike(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
