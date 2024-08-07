package com.kinzie.userservice.common.config;

import com.kinzie.userservice.common.security.JwtAuthenticationFilter;
import com.kinzie.userservice.common.security.JwtAuthorizationFilter;
import com.kinzie.userservice.common.security.UserDetailsServiceImpl;
import com.kinzie.userservice.common.util.JwtUtil;
import com.kinzie.userservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Slf4j
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;
    private final Environment env;
    @Autowired
    public SecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService,
                          AuthenticationConfiguration authenticationConfiguration, UserService userService,
                          Environment env) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userService = userService;
        this.env = env;

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(jwtUtil,authenticationManager(authenticationConfiguration),
                        userService,env);
//        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/",".actuator/**").permitAll() // 메인 페이지 요청 허가
                        .requestMatchers("/api/auth/re-issue","/api/auth/sign-out",
                                "/api/wishlist/**","/api/orders/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // '/api/v1/admin/'로 시작하는 요청은 ADMIN 권한 필요
                        .anyRequest().permitAll() // 그 외 요청 모두 접근 허가
        );

        // Exception Handling 설정 (access denied 처리)
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint()) // 인증 실패 시 처리
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    // 인가 실패인 경우
    class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication is required to access this resource.\"}");
            log.error("Unauthorized error: {}", authException.getMessage());
        }
    }
}