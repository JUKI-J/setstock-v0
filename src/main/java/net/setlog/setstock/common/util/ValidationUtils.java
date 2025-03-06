package net.setlog.setstock.common.util;

import net.setlog.setstock.common.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 데이터 유효성 검사 유틸리티 클래스
 */
public class ValidationUtils {

    private ValidationUtils() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    // 정규식 패턴 정의
    private static final Pattern STOCK_CODE_PATTERN = Pattern.compile("^[0-9]{6}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");

    /**
     * 객체가 null이 아닌지 검증
     * @param object 검증할 객체
     * @param message 예외 메시지
     */
    public static void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 문자열이 비어있지 않은지 검증
     * @param str 검증할 문자열
     * @param message 예외 메시지
     */
    public static void validateNotEmpty(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 컬렉션이 비어있지 않은지 검증
     * @param collection 검증할 컬렉션
     * @param message 예외 메시지
     */
    public static void validateNotEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 맵이 비어있지 않은지 검증
     * @param map 검증할 맵
     * @param message 예외 메시지
     */
    public static void validateNotEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 숫자가 양수인지 검증
     * @param number 검증할 숫자
     * @param message 예외 메시지
     */
    public static void validatePositive(BigDecimal number, String message) {
        if (number == null || number.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 숫자가 0 이상인지 검증
     * @param number 검증할 숫자
     * @param message 예외 메시지
     */
    public static void validateNotNegative(BigDecimal number, String message) {
        if (number == null || number.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 종목 코드 형식 검증 (6자리 숫자)
     * @param stockCode 검증할 종목 코드
     * @param message 예외 메시지
     */
    public static void validateStockCode(String stockCode, String message) {
        validateNotEmpty(stockCode, message);
        if (!STOCK_CODE_PATTERN.matcher(stockCode).matches()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 이메일 형식 검증
     * @param email 검증할 이메일
     * @param message 예외 메시지
     */
    public static void validateEmail(String email, String message) {
        validateNotEmpty(email, message);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 전화번호 형식 검증
     * @param phoneNumber 검증할 전화번호
     * @param message 예외 메시지
     */
    public static void validatePhoneNumber(String phoneNumber, String message) {
        validateNotEmpty(phoneNumber, message);
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 숫자가 지정된 범위 내에 있는지 검증
     * @param value 검증할 값
     * @param min 최소값
     * @param max 최대값
     * @param message 예외 메시지
     */
    public static void validateRange(BigDecimal value, BigDecimal min, BigDecimal max, String message) {
        if (value == null || value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 문자열 최소 길이 검증
     * @param str 검증할 문자열
     * @param minLength 최소 길이
     * @param message 예외 메시지
     */
    public static void validateMinLength(String str, int minLength, String message) {
        if (str == null || str.length() < minLength) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 문자열 최대 길이 검증
     * @param str 검증할 문자열
     * @param maxLength 최대 길이
     * @param message 예외 메시지
     */
    public static void validateMaxLength(String str, int maxLength, String message) {
        if (str != null && str.length() > maxLength) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 주문 수량 검증
     * @param quantity 주문 수량
     * @param maxQuantity 최대 주문 가능 수량
     */
    public static void validateOrderQuantity(int quantity, int maxQuantity) {
        if (quantity <= 0) {
            throw new BusinessException("주문 수량은 0보다 커야 합니다.", "INVALID_QUANTITY");
        }

        if (quantity > maxQuantity) {
            throw new BusinessException(
                String.format("주문 수량(%d)이 최대 주문 가능 수량(%d)을 초과했습니다.", quantity, maxQuantity),
                "QUANTITY_EXCEEDED"
            );
        }
    }

    /**
     * 거래 시간 검증
     * @param dateTime 검증할 날짜시간
     */
    public static void validateMarketOpen(LocalDateTime dateTime) {
        if (!DateTimeUtils.isMarketOpen(dateTime)) {
            throw new BusinessException("현재 거래 시간이 아닙니다.", "MARKET_CLOSED");
        }
    }

    /**
     * 계좌 잔고 충분 여부 검증
     * @param required 필요한 금액
     * @param available 사용 가능한 금액
     */
    public static void validateSufficientFunds(BigDecimal required, BigDecimal available) {
        if (required.compareTo(available) > 0) {
            throw new BusinessException(
                String.format("계좌 잔고가 부족합니다. 필요: %s, 가용: %s",
                    NumberUtils.formatCurrency(required),
                    NumberUtils.formatCurrency(available)),
                "INSUFFICIENT_FUNDS"
            );
        }
    }

    /**
     * 날짜 유효성 검증
     * @param date 검증할 날짜
     * @param message 예외 메시지
     */
    public static void validateDateNotInFuture(LocalDate date, String message) {
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }

    /**
     * 날짜 범위 유효성 검증
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @param message 예외 메시지
     */
    public static void validateDateRange(LocalDate startDate, LocalDate endDate, String message) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BusinessException(message, "VALIDATION_ERROR");
        }
    }
}