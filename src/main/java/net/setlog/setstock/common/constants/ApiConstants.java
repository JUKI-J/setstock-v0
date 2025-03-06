package net.setlog.setstock.common.constants;

/**
 * API 관련 상수 정의 클래스
 */
public class ApiConstants {

    private ApiConstants() {
        // 상수 클래스이므로 인스턴스화 방지
    }

    // 한국투자증권 API 관련 상수
    public static final String KIS_BASE_URL_REAL = "https://openapi.koreainvestment.com:9443";  // 한투 API 기본 URL 실전투자
    public static final String KIS_BASE_URL_VIRTUAL = "https://openapivts.koreainvestment.com:29443";  // 한투 API 기본 URL 모의투자
    public static final String KIS_REALTIME_URL_REAL = "ws://ops.koreainvestment.com:21000";    // 한투 실시간 웹소켓 URL 실전투자
    public static final String KIS_REALTIME_URL_VIRTUAL = "ws://ops.koreainvestment.com:31000";    // 한투 실시간 웹소켓 URL 모의투자

    // API 엔드포인트 경로
    public static final String KIS_TOKEN_PATH = "/oauth2/tokenP";  // 토큰 발급 경로
    public static final String KIS_PRICE_PATH = "/uapi/domestic-stock/v1/quotations/inquire-price";  // 시세 조회 경로
    public static final String KIS_ORDER_PATH = "/uapi/domestic-stock/v1/trading/order-cash";  // 주문 경로
    public static final String KIS_ACCOUNT_PATH = "/uapi/domestic-stock/v1/trading/inquire-balance";  // 계좌 조회 경로
    public static final String KIS_DAILY_PRICE_PATH = "/uapi/domestic-stock/v1/quotations/inquire-daily-price";  // 일별 시세 경로
    public static final String KIS_MINUTE_PRICE_PATH = "/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice";  // 분 시세 경로

    // API 헤더 키
    public static final String HEADER_AUTHORIZATION = "authorization";
    public static final String HEADER_APP_KEY = "appkey";
    public static final String HEADER_APP_SECRET = "appsecret";
    public static final String HEADER_TR_ID = "tr_id";
    public static final String HEADER_CONTENT_TYPE = "content-type";

    // 조회 TR ID
    public static final String TR_ID_PRICE = "FHKST01010100";  // 현재가 조회
    public static final String TR_ID_ACCOUNT = "TTTC8434R";    // 계좌 잔고 조회
    public static final String TR_ID_DAILY_PRICE = "FHKST01010400";  // 일별 시세 조회
    public static final String TR_ID_MINUTE_PRICE = "FHKST03010200";  // 분 시세 조회

    // 주문 TR ID
    public static final String TR_ID_BUY_ORDER = "TTTC0802U";   // 매수 주문
    public static final String TR_ID_SELL_ORDER = "TTTC0801U";  // 매도 주문
    public static final String TR_ID_CANCEL_ORDER = "TTTC0803U"; // 주문 취소

    // 웹소켓 TR ID
    public static final String TR_ID_WEBSOCKET_PRICE = "H0STCNT0";  // 현재가 실시간 조회

    // API 응답 코드
    public static final String API_RESPONSE_SUCCESS = "0";    // 성공
    public static final String API_RESPONSE_ERROR = "1";      // 에러

    // API 요청 제한 관련 상수
    public static final int API_MAX_REQUESTS_PER_SECOND = 5;   // 초당 최대 요청 수
    public static final int API_MAX_REQUESTS_PER_MINUTE = 100; // 분당 최대 요청 수

    // API 토큰 만료 시간 (8시간 = 28800초)
    public static final int API_TOKEN_EXPIRY_SECONDS = 28800;

    // 재시도 관련 상수
    public static final int API_MAX_RETRY_COUNT = 3;        // 최대 재시도 횟수
    public static final int API_RETRY_DELAY_MS = 1000;      // 재시도 간격 (밀리초)

    // 응답 지연 타임아웃
    public static final int API_CONNECTION_TIMEOUT_MS = 5000;   // 연결 타임아웃 (밀리초)
    public static final int API_READ_TIMEOUT_MS = 10000;        // 읽기 타임아웃 (밀리초)

    // 웹소켓 관련 상수
    public static final int WEBSOCKET_CONNECT_TIMEOUT_MS = 10000;  // 웹소켓 연결 타임아웃 (밀리초)
    public static final int WEBSOCKET_RECONNECT_DELAY_MS = 5000;   // 웹소켓 재연결 간격 (밀리초)
    public static final int WEBSOCKET_MAX_RECONNECT_ATTEMPTS = 5;  // 웹소켓 최대 재연결 시도 횟수

    // 기타 상수
    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";  // JSON 컨텐츠 타입
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded"; // 폼 컨텐츠 타입
}