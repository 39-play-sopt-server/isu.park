package org.sopt.Entity;


import java.time.LocalDate;

public class Post {
    Integer id; // post 구분자
    String title;  // 제목
    String content;  // 본문
    String author;  // 글쓴이
    LocalDate createdAt; // 생성일
    LocalDate updatedAt; // 수정일
    String category; // 카테고리

    public Post(String title, String content, String author, LocalDate createdAt, LocalDate updatedAt, String category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public String getCategory(){
        return this.category;
    }

    // 게시글 수정
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setUpdatedAt(LocalDate updatedAt){this.updatedAt = updatedAt;}
}