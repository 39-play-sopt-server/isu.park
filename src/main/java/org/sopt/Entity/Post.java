package org.sopt.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    protected Post() {}

    public Post(
            String title,
            String content,
            String author,
            LocalDate createdAt,
            LocalDate updatedAt,
            Category category
    ) {
        validate(title, content, category);  // 초기 검증
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
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

    // 게시글 수정 메소드
    public void update(
            String title,
            String content,
            String author,
            Category category,
            LocalDate updatedAt
    ){
        validate(title, content, category);  // 초기 검증
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.updatedAt = updatedAt;
    }

    // 게시글 검증 메소드
    public void validate(String title,String content,Category category){
        if (title == null || title.isEmpty()){
            throw new IllegalArgumentException("게시글 제목은 필수 입력 항목입니다.");
        }
        if (content == null || content.isEmpty()){
            throw new IllegalArgumentException("게시글 본문은 필수 입력 항목입니다.");
        }
        if (category == null){
            throw new IllegalArgumentException("게시글 카테고리는 필수 입력 항목입니다.");
        }
    }
}