package net.setlog.setstock.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 숫자 관련 유틸리티 클래스
 */
public class NumberUtils {

    private NumberUtils() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    /**
     * BigDecimal 반올림
     * @param value 대상 숫자
     * @param places 소수점 자릿수
     * @return 반올림된 값
     */
    public static BigDecimal round(BigDecimal value, int places) {
        if (value == null) return null;
        if (places < 0) throw new IllegalArgumentException("소수점 자릿수는 0 이상이어야 합니다");

        return value.setScale(places, RoundingMode.HALF_UP);
    }

    /**
     * 백분율 계산 (numerator / denominator * 100)
     * @param numerator 분자
     * @param denominator 분모
     * @return 백분율 (소수점 2자리)
     */
    public static BigDecimal calculatePercentage(BigDecimal numerator, BigDecimal denominator) {
        if (numerator == null || denominator == null) {
            return null;
        }

        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("분모는 0이 될 수 없습니다");
        }

        return numerator.divide(denominator, 4, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 숫자를 한국 통화 형식으로 포맷팅
     * @param amount 금액
     * @return 통화 형식 문자열 (예: ₩1,234,567)
     */
    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) return null;

        NumberFormat koreanWon = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return koreanWon.format(amount);
    }

    /**
     * 숫자를 지정된 소수점 자릿수로 포맷팅
     * @param number 숫자
     * @param decimalPlaces 소수점 자릿수
     * @return 포맷팅된 숫자 문자열
     */
    public static String formatNumber(BigDecimal number, int decimalPlaces) {
        if (number == null) return null;

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(decimalPlaces);
        numberFormat.setMinimumFractionDigits(decimalPlaces);
        return numberFormat.format(number);
    }

    /**
     * 숫자를 백분율 형식으로 포맷팅
     * @param percentage 백분율 (예: 12.34)
     * @return 포맷팅된 백분율 문자열 (예: 12.34%)
     */
    public static String formatPercentage(BigDecimal percentage) {
        if (percentage == null) return null;

        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);
        return percentFormat.format(percentage.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
    }

    /**
     * 숫자가 양수인지 확인
     * @param number 확인할 숫자
     * @return 양수 여부
     */
    public static boolean isPositive(BigDecimal number) {
        if (number == null) return false;
        return number.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 숫자가 음수인지 확인
     * @param number 확인할 숫자
     * @return 음수 여부
     */
    public static boolean isNegative(BigDecimal number) {
        if (number == null) return false;
        return number.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 숫자가 0인지 확인
     * @param number 확인할 숫자
     * @return 0 여부
     */
    public static boolean isZero(BigDecimal number) {
        if (number == null) return false;
        return number.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 두 숫자의 변화율 계산 ((current - previous) / previous * 100)
     * @param previous 이전 값
     * @param current 현재 값
     * @return 변화율 (%)
     */
    public static BigDecimal calculateChangeRate(BigDecimal previous, BigDecimal current) {
        if (previous == null || current == null) {
            return null;
        }

        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal("100");
        }

        return current.subtract(previous)
            .divide(previous, 4, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 숫자의 절대값 반환
     * @param number 숫자
     * @return 절대값
     */
    public static BigDecimal abs(BigDecimal number) {
        if (number == null) return null;
        return number.abs();
    }

    /**
     * 값이 주어진 범위 내에 있는지 확인
     * @param value 확인할 값
     * @param min 최소값
     * @param max 최대값
     * @return 범위 내 여부
     */
    public static boolean isInRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value == null || min == null || max == null) {
            return false;
        }

        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    /**
     * 값을 주어진 범위 내로 제한
     * @param value 제한할 값
     * @param min 최소값
     * @param max 최대값
     * @return 제한된 값
     */
    public static BigDecimal clamp(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value == null) return null;
        if (min == null && max == null) return value;

        if (min != null && value.compareTo(min) < 0) {
            return min;
        }

        if (max != null && value.compareTo(max) > 0) {
            return max;
        }

        return value;
    }
}