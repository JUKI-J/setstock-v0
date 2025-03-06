package net.setlog.setstock.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 리소스를 찾을 수 없을 때 발생하는 예외 클래스
 *
 * 데이터베이스나 외부 API에서 요청한 리소스를 찾을 수 없을 때 사용
 */
public class ResourceNotFoundException extends ApiException {

    /**
     * 생성자
     *
     * @param resourceName 리소스 이름 (예: "주식", "전략", "계좌" 등)
     * @param fieldName 필드 이름 (예: "id", "code" 등)
     * @param fieldValue 필드 값
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
            String.format("%s을(를) %s='%s'(으)로 찾을 수 없습니다", resourceName, fieldName, fieldValue),
            HttpStatus.NOT_FOUND,
            "RESOURCE_NOT_FOUND"
        );
    }

    /**
     * 생성자
     *
     * @param message 사용자 정의 메시지
     */
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    /**
     * 종목 찾기 실패 예외 생성
     *
     * @param stockId 종목 코드
     * @return ResourceNotFoundException 인스턴스
     */
    public static ResourceNotFoundException stockNotFound(String stockId) {
        return new ResourceNotFoundException("종목", "코드", stockId);
    }

    /**
     * 전략 찾기 실패 예외 생성
     *
     * @param strategyId 전략 ID
     * @return ResourceNotFoundException 인스턴스
     */
    public static ResourceNotFoundException strategyNotFound(Long strategyId) {
        return new ResourceNotFoundException("전략", "ID", strategyId);
    }

    /**
     * 주문 찾기 실패 예외 생성
     *
     * @param orderId 주문 ID
     * @return ResourceNotFoundException 인스턴스
     */
    public static ResourceNotFoundException orderNotFound(Long orderId) {
        return new ResourceNotFoundException("주문", "ID", orderId);
    }

    /**
     * 계좌 찾기 실패 예외 생성
     *
     * @param accountId 계좌 ID
     * @return ResourceNotFoundException 인스턴스
     */
    public static ResourceNotFoundException accountNotFound(String accountId) {
        return new ResourceNotFoundException("계좌", "ID", accountId);
    }
}