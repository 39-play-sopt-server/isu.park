package org.sopt.app.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.app.member.entity.Member;
import org.sopt.app.post.dto.PostCreateRequest;
import org.sopt.app.post.dto.PostListResponse;
import org.sopt.app.post.dto.PostResponse;
import org.sopt.app.post.dto.PostUpdateRequest;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.sopt.app.post.service.PostService;
import org.sopt.global.common.BaseResponse;
import org.sopt.global.common.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="게시물 관리", description = "게시물 CRUD 관련 API")
public interface PostApi {
    @Operation(
            summary = "게시물 생성", description = "게시물을 생성합니다.",
            requestBody = @RequestBody(
                    description = "게시물 생성 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(
                                            name = "게시글 생성 요청 예시",
                                            value = """
                                            {
                                                "title": "test",
                                                "content": "test",
                                                "author": "test",
                                                "category": "FREE"
                                            }
                                            """
                                    )})))
    @ApiResponses({
            @ApiResponse(  // 생성 성공
                    responseCode = "201", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "게시글 생성 성공", value = """
                            {
                                "status": 201,
                                "success": true,
                                "data": null,
                                "message": "리소스가 성공적으로 생성되었습니다."
                            }
                            """)})
            ),
            @ApiResponse(  // 카테고리가 비었거나 다른 값이 들어갔을 때
                    responseCode = "400", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "카테고리 누락 실패 응답", value = """
                            {
                                "status": 400,
                                "success": false,
                                "data": null,
                                "message": "유효하지 않은 카테고리입니다."
                            }
                            """
                            ),
                            @ExampleObject(name = "제목 누락 실패 응답", value = """
                            {
                                "status": 400,
                                "success": false,
                                "data": null,
                                "message": "게시글 제목은 필수 입력 항목입니다."
                            }
                            """
                            ),
                            @ExampleObject(name = "본문 누락 실패 응답", value = """
                            {
                                "status": 400,
                                "success": false,
                                "data": null,
                                "message": "게시글 본문은 필수 입력 항목입니다."
                            }
                            """)})
            )
    })
    ResponseEntity<BaseResponse<Void>> createPost(PostCreateRequest request);

    @Operation(
            summary = "게시물 목록 조회", description = "게시물 목록(번호, 제목)을 조회합니다.",
            parameters = {
                    @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0"),
                    @Parameter(name = "size", description = "한 페이지당 조회할 게시물 개수", example = "10"),
            })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 목록 조회 성공", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": [
                                            {
                                                "title": "test",
                                                "content": "test",
                                                "author": "test",
                                                "createdAt": "2026-01-01",
                                                "updatedAt": "2026-01-02",
                                                "category": "FREE",
                                                "id": 2
                                            }
                                        ],
                                        "message": "리소스가 성공적으로 조회되었습니다."
                                    }
                                    """),
                            @ExampleObject(name="게시글 목록 조회 실패(잘못된 page+size/ 데이터 없음)", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": [],
                                        "message": "리소스가 성공적으로 조회되었습니다."
                                    }
                                    """)
                    }))})
    ResponseEntity<BaseResponse<List<PostListResponse>>> getPosts(int page, int size);
    @Operation(
            summary = "게시글 상세 조회", description = "게시글 ID를 통해 게시물 정보를 조회합니다.",
            parameters = @Parameter(name = "postId", description = "조회할 게시글 ID", example = "1")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 상세 조회 성공", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": {
                                                "id": 1,
                                                "title": "test",
                                                "content": "test",
                                                "author": "test",
                                                "category": "FREE",
                                                "createdAt": "2026-01-01",
                                                "updatedAt": "2026-01-02"
                                        },
                                        "message": "리소스가 성공적으로 조회되었습니다."
                                    }
                            """)})
            ),
            @ApiResponse(
                    responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 상세 조회 실패", value = """
                                    {
                                        "status": 404,
                                        "success": false,
                                        "data": null,
                                        "message": "존재하지 않는 게시글입니다."
                                    }
                            """)})
            )
    })
    ResponseEntity<BaseResponse<PostResponse>> getPost(Long postId);

    @Operation(
            summary = "필터를 통한 게시물 검색", description = "지정한 필터를 통해 그에 맞는 게시물 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "category", description = "조회할 게시글의 카테고리", example = "FREE"),
                    @Parameter(name = "authorId", description = "조회할 게시글의 작성자 ID", example = "1"),
                    @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0"),
                    @Parameter(name = "size", description = "한 페이지당 조회할 게시물 개수", example = "10")
            }

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name="카테고리별 게시글 조회 성공", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": [
                                            {
                                                "title": "test1",
                                                "content": "test1",
                                                "author": "test1",
                                                "createdAt": "2026-01-01",
                                                "updatedAt": "2026-01-02",
                                                "category": "FREE",
                                                "id": 1
                                            },
                                            {
                                                "title": "test2",
                                                "content": "test2",
                                                "author": "test2",
                                                "createdAt": "2026-01-01",
                                                "updatedAt": "2026-01-02",
                                                "category": "FREE",
                                                "id": 2
                                            }
                                        ],
                                        "message": "리소스가 성공적으로 조회되었습니다.."
                                    }
                                    """)})
            )
    })
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostBySearch(Category category, Long authorId, int page, int size);
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostByTitle(String title);

    @Operation(
            summary = "게시글 수정", description = "게시글 ID를 통해 게시물 정보를 수정합니다.",
            parameters = @Parameter(name = "postId", description = "수정할 게시글 ID", example = "1")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 수정 성공", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": null,
                                        "message": "리소스가 성공적으로 업데이트되었습니다."
                                    }
                                    """)})
            ),
            @ApiResponse(
                    responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 수정 실패", value = """
                                    {
                                        "status": 404,
                                        "success": false,
                                        "data": null,
                                        "message": "존재하지 않는 게시글입니다."
                                    }
                            """)})
            )
    })
    ResponseEntity<BaseResponse<Void>> updatePost(Long postId, PostUpdateRequest request);

    @Operation(
            summary = "게시글 삭제", description = "게시글 ID를 통해 게시물을 삭제합니다.",
            parameters = @Parameter(name = "postId", description = "삭제할 게시글 Id", example = "1")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 삭제 성공", value = """
                                    {
                                        "status": 200,
                                        "success": true,
                                        "data": null,
                                        "message": "리소스가 성공적으로 삭제되었습니다."
                                    }
                                    """)})
            ),
            @ApiResponse(
                    responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name="게시글 삭제 실패", value = """
                                    {
                                        "status": 404,
                                        "success": false,
                                        "data": null,
                                        "message": "존재하지 않는 게시글입니다."
                                    }
                            """)})
            )
    })
    ResponseEntity<BaseResponse<Void>> deletePost(Long postId);
}

