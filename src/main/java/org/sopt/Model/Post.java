package org.sopt.Model;


public class Post {
    Integer id; // post 구분자
    String title;
    String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    // 게시글 조회 -> 제목, 본문 데이터 전달
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }

    // 게시글 수정 -> 제목, 본문 데이터 교체
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
}