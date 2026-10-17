package org.sopt.app.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;

import java.time.LocalDate;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // 제목
    private String content;  // 본문
    private String author;  // 글쓴이
    private LocalDate createdAt; // 생성일
    private LocalDate updatedAt; // 수정일
    private Category category; // 카테고리
    private Integer views = 0;  // 조회수

    protected Post() {}

    public Post(
            String title,
            String content,
            String author,
            LocalDate createdAt,
            LocalDate updatedAt,
            Category category
    ) {
        validate(title, content);  // 초기 검증
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.views = 0;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // 게시글 조회
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public String getAuthor(){
        return this.author;
    }
    public LocalDate getCreatedAt(){
        return this.createdAt;
    }
    public LocalDate getUpdatedAt(){
        return this.updatedAt;
    }
    public Category getCategory(){
        return this.category;
    }
    public Integer getViews(){
        return this.views;
    }

    // 게시글 수정 메소드
    public void update(
            String title,
            String content,
            String author,
            Category category,
            LocalDate updatedAt
    ){
        validate(title, content);  // 초기 검증
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.updatedAt = updatedAt;
    }

    // 게시글 검증 메소드
    public void validate(String title, String content){
        if (title == null || title.isEmpty()){
            throw new CustomException(ErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isEmpty()){
            throw new CustomException(ErrorCode.POST_CONTENT_EMPTY);
        }
    }

    // 조회수 증가 메소드
    public void increaseViews(){
        this.views++;
    }
}
