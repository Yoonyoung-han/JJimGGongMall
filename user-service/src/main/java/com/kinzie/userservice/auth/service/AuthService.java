package com.kinzie.userservice.auth.service;


import com.kinzie.userservice.auth.dto.MailDto;
import com.kinzie.userservice.auth.dto.request.AuthenticationRequest;
import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.auth.dto.request.SignUpRequestDto;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface AuthService {
    void sendEmail(MailDto request) throws MessagingException;

    void verifyMail(MailDto request);

    Map<String, String> signIn(AuthenticationRequest request);

    Map<String, String> checkRefreshToken(String refreshToken);

    User signUp(SignUpRequestDto request);
}
