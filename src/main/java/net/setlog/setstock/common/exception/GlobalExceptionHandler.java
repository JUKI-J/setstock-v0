package net.setlog.setstock.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

/**
 * 글로벌 예외 처리기
 *
 * 애플리케이션에서 발생하는 모든 예외를 중앙에서 처리하여 일관된 오류 응답 제공
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * ApiException 처리
     *
     * @param ex ApiException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        log.error("API 예외 발생: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(ex.getStatus().value())
            .error(ex.getStatus().getReasonPhrase())
            .errorCode(ex.getErrorCode())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * 유효성 검사 예외 처리
     *
     * @param ex MethodArgumentNotValidException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.error("유효성 검증 예외 발생: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode("VALIDATION_ERROR")
            .message("입력값 검증에 실패했습니다")
            .path(request.getRequestURI())
            .build();

        // 유효성 검사 실패 세부 정보 추가
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Bean Validation 예외 처리
     *
     * @param ex ConstraintViolationException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
        ConstraintViolationException ex, HttpServletRequest request) {

        log.error("제약 조건 위반 예외 발생: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode("VALIDATION_ERROR")
            .message("제약 조건 위반이 발생했습니다")
            .path(request.getRequestURI())
            .build();

        // 유효성 검사 실패 세부 정보 추가
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String field = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);

            errorResponse.addValidationError(
                field,
                violation.getInvalidValue(),
                violation.getMessage()
            );
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 폼 바인딩 예외 처리
     *
     * @param ex BindException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(
        BindException ex, HttpServletRequest request) {

        log.error("바인딩 예외 발생: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode("BINDING_ERROR")
            .message("데이터 바인딩에 실패했습니다")
            .path(request.getRequestURI())
            .build();

        // 바인딩 실패 세부 정보 추가
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 메소드 인자 타입 불일치 예외 처리
     *
     * @param ex MethodArgumentTypeMismatchException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        log.error("메소드 인자 타입 불일치 예외 발생: {}", ex.getMessage());

        String message = String.format("매개변수 '%s'의 값 '%s'을(를) '%s' 타입으로 변환할 수 없습니다",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode("TYPE_MISMATCH")
            .message(message)
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 요청 핸들러를 찾을 수 없는 예외 처리
     *
     * @param ex NoHandlerFoundException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
        NoHandlerFoundException ex, HttpServletRequest request) {

        log.error("요청 핸들러를 찾을 수 없음: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .errorCode("NOT_FOUND")
            .message(String.format("'%s' 경로에 대한 핸들러를 찾을 수 없습니다", ex.getRequestURL()))
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * 접근 거부 예외 처리
     *
     * @param ex AccessDeniedException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
        AccessDeniedException ex, HttpServletRequest request) {

        log.error("접근 거부 예외 발생: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN.value())
            .error(HttpStatus.FORBIDDEN.getReasonPhrase())
            .errorCode("FORBIDDEN")
            .message("이 리소스에 접근할 수 있는 권한이 없습니다")
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * REST 클라이언트 예외 처리
     *
     * @param ex RestClientException
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleRestClientException(
        RestClientException ex, HttpServletRequest request) {

        log.error("외부 API 호출 예외 발생: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .errorCode("EXTERNAL_API_ERROR")
            .message("외부 API 호출 중 오류가 발생했습니다: " + ex.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * 일반 예외 처리
     *
     * @param ex Exception
     * @param request HTTP 요청
     * @return 오류 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
        Exception ex, HttpServletRequest request) {

        log.error("처리되지 않은 예외 발생: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .errorCode("INTERNAL_ERROR")
            .message("서버 내부 오류가 발생했습니다")
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}