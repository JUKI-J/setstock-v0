package net.setlog.setstock.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 관련 예외 클래스
 *
 * 자동매매 시스템의 API 관련 예외를 처리하기 위한 기본 예외 클래스
 * 상태 코드와 에러 코드를 포함하여 클라이언트에 보다 상세한 오류 정보 제공
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    /**
     * ApiException 생성자
     *
     * @param message 예외 메시지
     * @param status HTTP 상태 코드
     * @param errorCode 오류 코드
     */
    public ApiException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    /**
     * ApiException 생성자 (원인 포함)
     *
     * @param message 예외 메시지
     * @param status HTTP 상태 코드
     * @param errorCode 오류 코드
     * @param cause 원인 예외
     */
    public ApiException(String message, HttpStatus status, String errorCode, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }

    /**
     * 비즈니스 로직 예외 생성
     *
     * @param message 예외 메시지
     * @param errorCode 오류 코드
     * @return ApiException 인스턴스
     */
    public static ApiException businessException(String message, String errorCode) {
        return new ApiException(message, HttpStatus.BAD_REQUEST, errorCode);
    }

    /**
     * 유효성 검사 예외 생성
     *
     * @param message 예외 메시지
     * @return ApiException 인스턴스
     */
    public static ApiException validationException(String message) {
        return new ApiException(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }

    /**
     * 리소스 찾을 수 없음 예외 생성
     *
     * @param resourceName 리소스 이름
     * @param fieldName 필드 이름
     * @param fieldValue 필드 값
     * @return ApiException 인스턴스
     */
    public static ApiException resourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        String message = String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue);
        return new ApiException(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    /**
     * 외부 API 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     * @return ApiException 인스턴스
     */
    public static ApiException externalApiException(String message, Throwable cause) {
        return new ApiException(message, HttpStatus.INTERNAL_SERVER_ERROR, "EXTERNAL_API_ERROR", cause);
    }
}