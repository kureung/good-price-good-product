package kr.co.gpgp.auth.config;

import kr.co.gpgp.auth.service.CustomOAuth2UserService;
import kr.co.gpgp.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final  AccessDeniedHandler CustomAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable().and()
                //authorizeRequests -> URL별 권한 관리 설정 옵션 시작점
                //antMatchers에 지정된 url들은 전체 열람 권한 줌
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/img/**", "/templates/img/*.png", "/js/**", "/h2-console/**", "/api/**")
                .permitAll()
                .antMatchers("/user").hasRole(Role.USER.name())
                .anyRequest().authenticated() //anyRequest 나머지 url들을 나타냄, authenticated 인증된 경우만 허용
                .and()
                .exceptionHandling()
                .accessDeniedHandler(CustomAccessDeniedHandler)// 권한이 없을 경우 저희가 custom 으로 만든 handler 를 추가
                .and()
                .logout().logoutSuccessUrl("/") //로그아웃 기능 설정 진입점, 로그아웃 성공 시 /로 이동
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/")//oauth2 인증 성공시 지정 경로
                .userInfoEndpoint().userService(customOAuth2UserService); //로그인 기능 설정 진입점, 소셜 로그인 성공 시 후속 조치 진행 Service 인터페이스 구현체 등록
    }

}
