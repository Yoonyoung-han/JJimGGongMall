package com.kinzie.userservice.auth.controller;

import com.kinzie.userservice.auth.dto.MailDto;
import com.kinzie.userservice.auth.dto.request.AuthenticationRequest;
import com.kinzie.userservice.auth.service.AuthService;
import com.kinzie.userservice.common.ApiResponse;
import com.kinzie.userservice.common.util.JwtUtil;
import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.auth.dto.request.SignUpRequestDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/health_check")
    public String status(HttpServletRequest request){
        return String.format("It's Working in Auth Service %s", request.getServerPort());
    }


    @PostMapping("/verification-code/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> sendVerificationCode(@RequestBody MailDto request) {
        try {
            authService.sendEmail(request);
            return ApiResponse.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to send verification code. Please try again later. \n " + e.getMessage());
        }
    }

    @PostMapping("/verification-code/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<String> checkVerification(@RequestBody MailDto request) {
        try {
            authService.verifyMail(request);
            return ApiResponse.ok("Email verified successfully.");
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // security의 기본 login 사용하고 싶지 않은 경우
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Map<String, String>> signIn(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        Map<String, String> tokens = authService.signIn(request);

        // 응답에 쿠키 추가
        response.addCookie(setAccessTokenCookie(tokens));

        // 응답 헤더에 액세스 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, tokens.get("accessToken"));
        headers.add("username", request.getAccountName());

        return ApiResponse.create();
    }

    @PutMapping("/re-issue")
    public ApiResponse<Map<String, String>> reIssueToken(@CookieValue("refresh-token") String refreshToken,
                                                         @CookieValue("access-token") String accessToken,
                                                         HttpServletResponse response) {

        Map<String, String> tokens = authService.checkRefreshToken(refreshToken);

        // 응답에 쿠키 추가
        response.addCookie(setAccessTokenCookie(tokens));

        // 응답 헤더에 액세스 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, tokens.get("accessToken"));

        return ApiResponse.of(HttpStatus.CREATED,headers,tokens);
    }

    public Cookie setAccessTokenCookie(Map<String, String> tokens){
        // 액세스 토큰 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", Base64.getUrlEncoder().encodeToString(tokens.get("accessToken").getBytes()));

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유효

        return accessTokenCookie;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<String> signUp(@RequestBody SignUpRequestDto request) {
        try{
            User savedUser = authService.signUp(request);
            return ApiResponse.ok("Sign up Success!!");
        } catch (Exception e){
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
