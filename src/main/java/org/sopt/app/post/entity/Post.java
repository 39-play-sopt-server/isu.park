package org.sopt.app.post.entity;


import org.sopt.global.common.ErrorCode;
import org.sopt.global.exception.CustomException;

import java.time.LocalDate;

public class Post {
    Long id;
    String title;
    String content;
    String author;
    LocalDate createdAt; // 생성일
    LocalDate updatedAt; // 수정일
    Category category;
    Integer views;  // 조회수 필드 추가

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
    public void validate(String title, String content, Category category){
        if (title == null || title.isEmpty()){
            throw new CustomException(ErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isEmpty()){
            throw new CustomException(ErrorCode.POST_CONTENT_EMPTY);
        }
        /*if (category == null){
            throw new CustomException(ErrorCode.POST_CATEGORY_EMPTY);
        }*/
    }
    // 조회수 증가 메소드
    public void increaseViews(){
        this.views++;
    }
}