package org.sopt.Dto;

public class ApiResponse<T> {
    private final boolean success;  // 성공 여부
    private final String message;  // 안내 메시지
    private final T data;  // 실제 전달 데이터

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 성공 응답 - 전달할 데이터 존재
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", data);
    }
    // 성공 응답 - only 메시지
    public static <T> ApiResponse<T> success(String message){
        return new ApiResponse<>(true, message, null);
    }
    // 실패 응답 - 예외 발생
    public static <T> ApiResponse<T> error(String errorMessage){
        return new ApiResponse<>(false,errorMessage,null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
