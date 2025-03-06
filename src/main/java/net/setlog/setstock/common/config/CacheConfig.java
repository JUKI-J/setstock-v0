package net.setlog.setstock.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 캐시 설정 클래스
 * 자주 사용하는 데이터의 빠른 접근을 위한 인메모리 캐시 설정을 정의
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Caffeine 캐시 설정 빈
     * @return Caffeine 객체
     */
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)  // 데이터 쓰기 후 5분 후 만료
            .expireAfterAccess(10, TimeUnit.MINUTES)  // 마지막 접근 후 10분 후 만료
            .initialCapacity(100)  // 초기 캐시 크기
            .maximumSize(1000)  // 최대 캐시 크기
            .recordStats();  // 캐시 통계 기록
    }

    /**
     * 캐시 매니저 빈 설정
     * @return CacheManager 객체
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineConfig());

        // 캐시 이름 설정
        cacheManager.setCacheNames(Arrays.asList(
            "stockCache",       // 종목 정보 캐시
            "priceCache",       // 가격 정보 캐시
            "candleCache",      // 캔들 데이터 캐시
            "indicatorCache",   // 기술적 지표 캐시
            "accountCache",     // 계좌 정보 캐시
            "tokenCache"        // API 토큰 캐시
        ));

        return cacheManager;
    }
}