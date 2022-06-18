package kr.co.gpgp.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// "/**" 자원 요청에 대해
// classpath 기본 설정을 추가 및 변경
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/","classpath:/static/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }
}
