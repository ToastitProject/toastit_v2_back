package org.toastit_v2.core.common.application.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.toastit_v2.core.security.web.filter.TokenAuthenticationFilter;
import org.toastit_v2.core.security.web.AuthenticationEntryPointImpl;
import org.toastit_v2.core.common.application.util.JwtTokenizer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    // 모든 유저 허용 페이지
    private final String[] allAllowPage = new String[]{
            "/", // 메인페이지
            "/error", // 에러페이지
            "/test/**", // 테스트 페이지
            "/swagger-ui.html", // Custom Swagger Ui Path
            "/swagger-ui/**", // Swagger UI
            "/v2/toastit/docs/**", // Swagger API docs
            "/swagger-resources/**", // Swagger resources
            "/swagger-ui.html", // Swagger HTML
            "/webjars/**",// Webjars for Swagger
            "/swagger/**",// Swagger try it out
            "/actuator/**", // Actuator for Prometheus
            "/user/**",
            "/token/**",
            "/cocktails/**" // 기본 칵테일 페이지. 테스트를 위해 일단 전부 허용
    };

    // 비로그인 유저 허용 페이지
    private final String[] notLoggedAllowPage = new String[]{
            "/user/login", // 로그인 페이지
            "/user/join", // 회원가입 페이지
            "/email/**" // 이메일 페이지
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 유저별 페이지 접근 허용
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(allAllowPage).permitAll()
                                              .requestMatchers(notLoggedAllowPage).not().authenticated()
                                              .anyRequest().authenticated();
        });

        //// 인증 실패 시 호출하는 AuthenticationEntryPoint 설정
        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint(new AuthenticationEntryPointImpl());
        });

        // 세션 관리 Stateless 설정(서버가 클라이언트 상태 저장x)
        http.sessionManagement(auth -> {
            auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // corf 비활성화
        http.csrf(csrf -> {
            csrf.disable();
        });

        // cors 허용
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        // 로그인 폼 비활성화
        http.formLogin(auth -> {
            auth.disable();
        });

        // http 기본 인증(헤더) 비활성화
        http.httpBasic(auth -> {
            auth.disable();
        });

        // JWT 필터 사용
        http.addFilterBefore(new TokenAuthenticationFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
