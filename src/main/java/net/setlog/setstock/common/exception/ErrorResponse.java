package net.setlog.setstock.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * API 오류 응답 클래스
 *
 * 클라이언트에게 일관된 형식의 오류 응답을 제공하기 위한 모델 클래스
 */
@Getter
@ToString
@Builder
public class ErrorResponse {

    /**
     * 오류 발생 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    /**
     * HTTP 상태 코드
     */
    private final int status;

    /**
     * HTTP 오류 메시지
     */
    private final String error;

    /**
     * 비즈니스 오류 코드
     */
    private final String errorCode;

    /**
     * 오류 메시지
     */
    private final String message;

    /**
     * 요청 경로
     */
    private final String path;

    /**
     * 유효성 검사 오류 목록 (있는 경우)
     */
    @Builder.Default
    private final List<ValidationError> validationErrors = new ArrayList<>();

    /**
     * 유효성 검사 오류 정보를 담는 내부 클래스
     */
    @Getter
    @Builder
    public static class ValidationError {

        /**
         * 오류가 발생한 필드 명
         */
        private final String field;

        /**
         * 거부된 값
         */
        private final Object rejectedValue;

        /**
         * 오류 메시지
         */
        private final String message;
    }

    /**
     * 유효성 검사 오류 추가 메서드
     *
     * @param field 필드 이름
     * @param rejectedValue 거부된 값
     * @param message 오류 메시지
     */
    public void addValidationError(String field, Object rejectedValue, String message) {
        this.validationErrors.add(ValidationError.builder()
            .field(field)
            .rejectedValue(rejectedValue)
            .message(message)
            .build());
    }

    /**
     * Builder 패턴에서 timestamp를 현재 시간으로 자동 설정
     */
    public static class ErrorResponseBuilder {
        private LocalDateTime timestamp = LocalDateTime.now();
    }
}