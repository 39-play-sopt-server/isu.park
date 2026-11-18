package org.sopt.app.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.app.member.entity.Member;
import org.sopt.app.post.dto.PostResponse;
import org.sopt.global.common.BaseEntity;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Column
    private String title;
    @Column
    private String content;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private Integer views = 0;
    @Column(nullable = false)
    private Integer likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(
            String title,
            String content,
            Member member,
            Category category
    ) {
        validate(title, content);  // 초기 검증
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
        this.views = 0;
    }

    // 게시글 수정 메소드
    public void update(
            String title,
            String content,
            Category category
    ){
        validate(title, content);  // 초기 검증
        this.title = title;
        this.content = content;
        this.category = category;
    }

    // 게시글 검증 메소드
    public void validate(String title, String content){
        if (title == null || title.isBlank()){
            throw new CustomException(ErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isBlank()){
            throw new CustomException(ErrorCode.POST_CONTENT_EMPTY);
        }
    }

    public String getAuthorName(){
        if(member == null || member.isWithdrawn()){
            return "탈퇴한 회원";
        }
        return member.getName();
    }

    public void increaseViews(){
        this.views = (this.views == null ? 0 : this.views) + 1;
    }
    public void increaseLikes(){
        this.likes = (this.likes == null ? 0 : this.likes) + 1;
    }
    public void decreaseLikes(){this.likes = (this.likes == null ? 0 : this.likes) - 1;}
    public PostResponse toResponse() {
        return new PostResponse(
                this.id,
                this.title,
                this.content,
                this.getAuthorName(),
                this.category,
                this.createdAt,
                this.updatedAt,
                this.views,
                this.likes
        );
    }
}
