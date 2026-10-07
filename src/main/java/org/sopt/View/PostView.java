package org.sopt.View;

import org.sopt.Model.Post;
import java.util.Scanner;

// 사용자 인터페이스 및 입출력 담당

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu(){
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public int readCommand(){
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readTitle(){
        System.out.print("제목: ");
        return scanner.nextLine();
    }
    public String readContent(){
        System.out.print("내용: ");
        return scanner.nextLine();
    }
    public String readAuthor(){
        System.out.print("저자: ");
        return scanner.nextLine();
    }
    public int readPostNumber(String message){
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }
    public void printPost(Post post){
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("저자: "+ post.getAuthor());
        System.out.println("저자: "+ post.getDate());
    }
    public void printMessage(String message){
        System.out.println(message);
    }
}
