package net.setlog.setstock.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * 날짜와 시간 관련 유틸리티 클래스
 */
public class DateTimeUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    // 한국 주식시장 거래 시간
    private static final LocalTime MARKET_OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime MARKET_CLOSE_TIME = LocalTime.of(15, 30);

    private DateTimeUtils() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    /**
     * 날짜를 문자열로 포맷팅
     * @param date 날짜
     * @return 포맷팅된 문자열
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER);
    }

    /**
     * 시간을 문자열로 포맷팅
     * @param time 시간
     * @return 포맷팅된 문자열
     */
    public static String formatTime(LocalTime time) {
        if (time == null) return null;
        return time.format(TIME_FORMATTER);
    }

    /**
     * 날짜와 시간을 문자열로 포맷팅
     * @param dateTime 날짜와 시간
     * @return 포맷팅된 문자열
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * 문자열을 날짜로 파싱
     * @param dateStr 날짜 문자열
     * @return 파싱된 날짜
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 문자열을 시간으로 파싱
     * @param timeStr 시간 문자열
     * @return 파싱된 시간
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    /**
     * 문자열을 날짜와 시간으로 파싱
     * @param dateTimeStr 날짜와 시간 문자열
     * @return 파싱된 날짜와 시간
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 주말인지 확인
     * @param date 날짜
     * @return 주말이면 true, 아니면 false
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * 주가 거래 시간인지 확인
     * @param dateTime 날짜와 시간
     * @return 거래 시간이면 true, 아니면 false
     */
    public static boolean isMarketOpen(LocalDateTime dateTime) {
        if (dateTime == null) return false;

        // 주말이면 시장 닫힘
        if (isWeekend(dateTime.toLocalDate())) {
            return false;
        }

        // 한국 주식시장 거래 시간: 9:00 ~ 15:30
        LocalTime time = dateTime.toLocalTime();
        return !time.isBefore(MARKET_OPEN_TIME) && time.isBefore(MARKET_CLOSE_TIME);
    }

    /**
     * 현재 한국 시간 기준으로 주가 거래 시간인지 확인
     * @return 거래 시간이면 true, 아니면 false
     */
    public static boolean isMarketOpenNow() {
        return isMarketOpen(LocalDateTime.now(KOREA_ZONE));
    }

    /**
     * 두 날짜와 시간 사이의 분 차이 계산
     * @param start 시작 날짜와 시간
     * @param end 종료 날짜와 시간
     * @return 분 차이
     */
    public static long minutesBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.MINUTES.between(start, end);
    }

    /**
     * 특정 날짜의 마켓 오픈 시간 반환
     * @param date 날짜
     * @return 마켓 오픈 시간을 포함한 날짜와 시간
     */
    public static LocalDateTime getMarketOpenTime(LocalDate date) {
        if (date == null) return null;
        return LocalDateTime.of(date, MARKET_OPEN_TIME);
    }

    /**
     * 특정 날짜의 마켓 클로즈 시간 반환
     * @param date 날짜
     * @return 마켓 클로즈 시간을 포함한 날짜와 시간
     */
    public static LocalDateTime getMarketCloseTime(LocalDate date) {
        if (date == null) return null;
        return LocalDateTime.of(date, MARKET_CLOSE_TIME);
    }

    /**
     * 현재 날짜와 시간을 한국 시간대로 반환
     * @return 한국 시간대의 현재 날짜와 시간
     */
    public static LocalDateTime nowKorea() {
        return LocalDateTime.now(KOREA_ZONE);
    }

    /**
     * 지정된 분 간격으로 날짜와 시간 목록 생성
     * @param start 시작 날짜와 시간
     * @param end 종료 날짜와 시간
     * @param minutesInterval 분 간격
     * @return 날짜와 시간 목록
     */
    public static List<LocalDateTime> createTimeSeriesWithInterval(
        LocalDateTime start, LocalDateTime end, int minutesInterval) {

        List<LocalDateTime> timeSeries = new ArrayList<>();
        LocalDateTime current = start;

        while (!current.isAfter(end)) {
            timeSeries.add(current);
            current = current.plusMinutes(minutesInterval);
        }

        return timeSeries;
    }

    /**
     * 최근 거래일 목록 생성 (주말 제외)
     * @param endDate 종료 날짜
     * @param count 거래일 수
     * @return 거래일 목록
     */
    public static List<LocalDate> getRecentTradingDays(LocalDate endDate, int count) {
        List<LocalDate> tradingDays = new ArrayList<>();
        LocalDate currentDate = endDate;

        while (tradingDays.size() < count) {
            if (!isWeekend(currentDate)) {
                tradingDays.add(currentDate);
            }
            currentDate = currentDate.minusDays(1);
        }

        return tradingDays;
    }

    /**
     * 특정 날짜가 속한 월의 첫 번째 거래일 반환
     * @param date 날짜
     * @return 첫 번째 거래일
     */
    public static LocalDate getFirstTradingDayOfMonth(LocalDate date) {
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());

        // 주말이면 다음 월요일로 조정
        if (isWeekend(firstDay)) {
            return firstDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return firstDay;
    }

    /**
     * 특정 날짜가 속한 월의 마지막 거래일 반환
     * @param date 날짜
     * @return 마지막 거래일
     */
    public static LocalDate getLastTradingDayOfMonth(LocalDate date) {
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());

        // 주말이면 이전 금요일로 조정
        if (isWeekend(lastDay)) {
            return lastDay.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        }

        return lastDay;
    }

    /**
     * 시간대를 한국 시간대로 변환
     * @param dateTime 날짜와 시간
     * @return 한국 시간대의 ZonedDateTime
     */
    public static ZonedDateTime toKoreaZonedDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return ZonedDateTime.of(dateTime, KOREA_ZONE);
    }
}