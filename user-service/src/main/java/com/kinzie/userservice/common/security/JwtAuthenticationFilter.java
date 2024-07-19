package com.kinzie.userservice.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinzie.userservice.auth.dto.request.AuthenticationRequest;
import com.kinzie.userservice.common.util.JwtUtil;
import com.kinzie.userservice.user.dto.UserDto;
import com.kinzie.userservice.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j(topic = "JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private UserService userService;
    private Environment env;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil,AuthenticationManager authenticationManager,
                                   UserService userService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.env = env;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getAccountName(),
                            requestDto.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUserId();
        UserDto userDetails = userService.getUserDetailsByUsername(username);

        String token = jwtUtil.generateAccessToken(userDetails.getUsername(), userDetails.getRole(), userId);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.addHeader("username", userDetails.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
