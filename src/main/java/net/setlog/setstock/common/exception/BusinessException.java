package net.setlog.setstock.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 비즈니스 로직 예외 클래스
 *
 * 자동매매 시스템의 비즈니스 로직에서 발생하는 예외를 처리하기 위한 클래스
 * 기본적으로 BAD_REQUEST(400) 상태 코드를 사용하는 ApiException의 확장
 */
public class BusinessException extends ApiException {

    /**
     * 기본 생성자
     *
     * @param message 예외 메시지
     */
    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BUSINESS_ERROR");
    }

    /**
     * 오류 코드 지정 생성자
     *
     * @param message 예외 메시지
     * @param errorCode 오류 코드
     */
    public BusinessException(String message, String errorCode) {
        super(message, HttpStatus.BAD_REQUEST, errorCode);
    }

    /**
     * 원인 지정 생성자
     *
     * @param message 예외 메시지
     * @param errorCode 오류 코드
     * @param cause 원인 예외
     */
    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, errorCode, cause);
    }

    /**
     * 주문 관련 비즈니스 예외 생성
     *
     * @param message 예외 메시지
     * @return BusinessException 인스턴스
     */
    public static BusinessException orderException(String message) {
        return new BusinessException(message, "ORDER_ERROR");
    }

    /**
     * 전략 관련 비즈니스 예외 생성
     *
     * @param message 예외 메시지
     * @return BusinessException 인스턴스
     */
    public static BusinessException strategyException(String message) {
        return new BusinessException(message, "STRATEGY_ERROR");
    }

    /**
     * 계좌 잔고 부족 예외 생성
     *
     * @param required 필요한 금액
     * @param actual 실제 잔고
     * @return BusinessException 인스턴스
     */
    public static BusinessException insufficientFundsException(double required, double actual) {
        String message = String.format("계좌 잔고가 부족합니다. 필요: %.2f, 실제: %.2f", required, actual);
        return new BusinessException(message, "INSUFFICIENT_FUNDS");
    }

    /**
     * 거래 시간 외 예외 생성
     *
     * @return BusinessException 인스턴스
     */
    public static BusinessException marketClosedException() {
        return new BusinessException("현재 거래 시간이 아닙니다.", "MARKET_CLOSED");
    }

    /**
     * 주문 수량 초과 예외 생성
     *
     * @param maxQuantity 최대 가능 수량
     * @return BusinessException 인스턴스
     */
    public static BusinessException quantityExceededException(int maxQuantity) {
        String message = String.format("주문 수량이 최대 가능 수량(%d)을 초과했습니다.", maxQuantity);
        return new BusinessException(message, "QUANTITY_EXCEEDED");
    }
}