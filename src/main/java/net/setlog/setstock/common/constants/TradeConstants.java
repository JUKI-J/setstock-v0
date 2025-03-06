package net.setlog.setstock.common.constants;

import java.math.BigDecimal;

/**
 * 거래 관련 상수 정의 클래스
 */
public class TradeConstants {

    private TradeConstants() {
        // 상수 클래스이므로 인스턴스화 방지
    }

    // 거래 유형
    public static final String ORDER_TYPE_MARKET = "MARKET";  // 시장가
    public static final String ORDER_TYPE_LIMIT = "LIMIT";    // 지정가
    public static final String ORDER_TYPE_CONDITIONAL = "CONDITIONAL";  // 조건부 주문

    // 거래 방향
    public static final String DIRECTION_BUY = "BUY";    // 매수
    public static final String DIRECTION_SELL = "SELL";  // 매도

    // 주문 상태
    public static final String ORDER_STATUS_CREATED = "CREATED";        // 생성됨
    public static final String ORDER_STATUS_SUBMITTED = "SUBMITTED";    // 제출됨
    public static final String ORDER_STATUS_ACCEPTED = "ACCEPTED";      // 접수됨
    public static final String ORDER_STATUS_PARTIALLY_FILLED = "PARTIALLY_FILLED";  // 부분 체결
    public static final String ORDER_STATUS_FILLED = "FILLED";          // 체결됨
    public static final String ORDER_STATUS_CANCELLED = "CANCELLED";    // 취소됨
    public static final String ORDER_STATUS_REJECTED = "REJECTED";      // 거부됨
    public static final String ORDER_STATUS_EXPIRED = "EXPIRED";        // 만료됨

    // 포지션 상태
    public static final String POSITION_STATUS_OPEN = "OPEN";      // 개설됨
    public static final String POSITION_STATUS_CLOSED = "CLOSED";  // 종료됨

    // 기본 수수료율 (0.015% = 0.00015)
    public static final BigDecimal DEFAULT_FEE_RATE = new BigDecimal("0.00015");

    // 세금율 (주식거래세 0.23% = 0.0023)
    public static final BigDecimal TAX_RATE = new BigDecimal("0.0023");

    // 시장 지표 관련 상수
    public static final int SHORT_TERM_SMA_PERIOD = 20;   // 단기 이동평균선 기간
    public static final int MEDIUM_TERM_SMA_PERIOD = 60;  // 중기 이동평균선 기간
    public static final int LONG_TERM_SMA_PERIOD = 120;   // 장기 이동평균선 기간

    public static final int RSI_PERIOD = 14;                          // RSI 기간
    public static final BigDecimal RSI_OVERSOLD = new BigDecimal("30");    // RSI 과매도 기준
    public static final BigDecimal RSI_OVERBOUGHT = new BigDecimal("70");  // RSI 과매수 기준

    // 리스크 관리 관련 상수
    public static final BigDecimal DEFAULT_STOP_LOSS_PERCENTAGE = new BigDecimal("0.02");  // 기본 손절매 비율 (2%)
    public static final BigDecimal DEFAULT_TAKE_PROFIT_PERCENTAGE = new BigDecimal("0.03"); // 기본 익절매 비율 (3%)
    public static final BigDecimal MAX_POSITION_SIZE_PERCENTAGE = new BigDecimal("0.1");    // 최대 포지션 크기 비율 (10%)

    // 캔들 타입
    public static final String CANDLE_TYPE_1MIN = "1min";
    public static final String CANDLE_TYPE_3MIN = "3min";
    public static final String CANDLE_TYPE_5MIN = "5min";
    public static final String CANDLE_TYPE_15MIN = "15min";
    public static final String CANDLE_TYPE_30MIN = "30min";
    public static final String CANDLE_TYPE_1HOUR = "1hour";
    public static final String CANDLE_TYPE_1DAY = "1day";

    // 전략 유형
    public static final String STRATEGY_TYPE_SHORT_TERM = "SHORT_TERM";    // 단타 전략
    public static final String STRATEGY_TYPE_TREND_FOLLOWING = "TREND_FOLLOWING";  // 추세추종 전략
    public static final String STRATEGY_TYPE_MEAN_REVERSION = "MEAN_REVERSION";    // 평균회귀 전략
    public static final String STRATEGY_TYPE_BREAKOUT = "BREAKOUT";        // 돌파 전략
    public static final String STRATEGY_TYPE_CUSTOM = "CUSTOM";            // 사용자 정의 전략

    // 신호 유형
    public static final String SIGNAL_TYPE_BUY = "BUY";      // 매수 신호
    public static final String SIGNAL_TYPE_SELL = "SELL";    // 매도 신호
    public static final String SIGNAL_TYPE_HOLD = "HOLD";    // 홀딩 신호

    // 시간 관련 상수
    public static final int MAX_POSITION_DURATION_MINUTES = 60;  // 최대 포지션 유지 시간 (분)

    // 기타 상수
    public static final int MAX_ORDERS_PER_STRATEGY = 10;  // 전략당 최대 주문 수
    public static final int MAX_ACTIVE_STRATEGIES = 10;    // 최대 활성 전략 수
}