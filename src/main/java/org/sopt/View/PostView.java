package org.sopt.View;

import org.sopt.Dto.ApiResponse;
import org.sopt.Model.Post;

import java.util.List;
import java.util.Scanner;

// 사용자 인터페이스 및 입출력 담당

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    // [입력] 관련 메서드
    public void printMenu(){
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }
    public int readCommand(){   // 메뉴 선택
        try{
            System.out.print("선택: ");
            return Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){  // 메뉴 번호 대신, 문자 입력할 경우
            return -1;
        }
    }
    public String readTitle(){   // 제목 작성
        System.out.print("제목: ");
        return scanner.nextLine();
    }
    public String readContent(){  // 내용 작성
        System.out.print("내용: ");
        return scanner.nextLine();
    }
    public String readAuthor(){  // 저자 작성
        System.out.print("저자: ");
        return scanner.nextLine();
    }
    public String readCategory(){  // 저자 작성
        System.out.print("카테고리: ");
        return scanner.nextLine();
    }
    public int readPostNumber(String message){   // 게시물 번호 작성
        try{
            System.out.print(message);
            return Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){  // 게시물 번호 대신, 문자 입력할 경우
            return -1;
        }
    }

    // [출력] 관련 메서드
    public void printMessage(String message){
        System.out.println(message);
    }
    public void renderResponse(ApiResponse<?> response){
        System.out.println(response.getMessage());
    }
    public void renderPostListResponse(ApiResponse<List<Post>> response){
        if(!response.isSuccess()) {
            System.out.println(response.getMessage());
            return;
        }
        List<Post> posts = response.getData();
        for(Post post : posts){
            printMessage(post.getId()+". "+post.getTitle());
        }
    }
    public void renderPostResponse(ApiResponse<Post> response){
        if(!response.isSuccess()) {
            System.out.println(response.getMessage());
            return;
        }
        Post post = response.getData();
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("카데고리: " + post.getCategory());
        System.out.println("저자: "+ post.getAuthor());
        System.out.println("작성일: "+ post.getDate());
    }
}
