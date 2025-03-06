package net.setlog.setstock.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 MVC 설정
 * 정적 리소스, CORS, 뷰 컨트롤러 등의 웹 관련 설정을 정의
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS(Cross-Origin Resource Sharing) 매핑 설정
     *
     * @param registry CORS 레지스트리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }

    /**
     * 정적 리소스 핸들러 설정
     *
     * @param registry 리소스 핸들러 레지스트리
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/");

        // 대시보드용 정적 리소스 설정
        registry.addResourceHandler("/dashboard/**")
            .addResourceLocations("classpath:/templates/dashboard/");
    }

    /**
     * 뷰 컨트롤러 설정
     *
     * @param registry 뷰 컨트롤러 레지스트리
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/dashboard");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/dashboard").setViewName("dashboard/index");
    }
}