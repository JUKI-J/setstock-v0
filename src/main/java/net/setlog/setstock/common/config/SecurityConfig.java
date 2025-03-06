package net.setlog.setstock.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 스프링 시큐리티 설정 클래스
 * 인증, 권한, 세션 관리 등의 보안 설정을 정의
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 보안 필터 체인 설정
     * @param http HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception 보안 설정 중 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)  // API 서버는 CSRF 보호가 필요 없음
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/public/**").permitAll()  // 공개 API는 모두 허용
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // 관리자 API는 ADMIN 롤 필요
                .requestMatchers("/api/**").authenticated()  // 그 외 API는 인증 필요
                .requestMatchers("/static/**", "/favicon.ico").permitAll()  // 정적 리소스 허용
                .anyRequest().authenticated()  // 나머지 요청은 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/login")  // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/dashboard")  // 로그인 성공 시 리다이렉션
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // REST API를 위한 세션 관리 설정
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            );

        return http.build();
    }

    /**
     * 패스워드 인코더 빈 설정
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}