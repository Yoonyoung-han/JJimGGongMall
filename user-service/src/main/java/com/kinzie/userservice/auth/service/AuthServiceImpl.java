package com.kinzie.userservice.auth.service;

import com.kinzie.userservice.auth.dto.MailDto;
import com.kinzie.userservice.auth.dto.request.AuthenticationRequest;
import com.kinzie.userservice.common.util.JwtUtil;
import com.kinzie.userservice.common.util.RedisUtil;
import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.auth.dto.request.SignUpRequestDto;
import com.kinzie.userservice.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;
    private RedisUtil redisUtil;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(JavaMailSender mailSender, PasswordEncoder passwordEncoder,TemplateEngine templateEngine,
                           RedisUtil redisUtil, JwtUtil jwtUtil, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.templateEngine = templateEngine;
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void sendEmail(MailDto mailDto)  throws MessagingException  {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailDto.getEmail());
        helper.setSubject("찜꽁몰에서 메일 인증 코드를 확인하세요.");

        Context context = new Context();
        String authCode = createdCode();
        context.setVariable("code", authCode);

        String html = templateEngine.process("emailTemplate", context);
        helper.setText(html, true);

        redisUtil.setDataExpire(mailDto.getEmail(), authCode, 60 * 10L);

        mailSender.send(message);
    }

    //난수를 만드는 메소드 0~9와 a~z까지의 숫자와 문자를 섞어서 6자리 난수를 만든다.
    private String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <=57 || i >=65) && (i <= 90 || i>= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void verifyMail(MailDto request) {
        String authNum = redisUtil.getData(request.getEmail());

        if (authNum == null || !authNum.equals(request.getCode())) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        // 인증 성공 로직
        log.info("Email verification successful for {}", request.getEmail());

        // Redis에서 데이터 삭제 (인증 완료 후 데이터는 필요 없으므로 삭제)
        redisUtil.deleteData(request.getEmail());
    }

    public Map<String, String> checkRefreshToken(String refreshToken){
        if (jwtUtil.validateToken(refreshToken)){
            Claims userInfo = jwtUtil.getUserInfoFromToken(refreshToken);
            String accountName = userInfo.get("username",String.class);
            String newAccessToken = jwtUtil.generateAccessToken(accountName,
                    userInfo.get("role",String.class), userInfo.get("userId",Long.class));

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    @Override
    public User signUp(SignUpRequestDto request) {
        request.encodingPassword(passwordEncoder);
        User user = User.of(request);
        return userRepository.save(user);
    }


    @Override
    public Map<String, String> signIn(AuthenticationRequest request) {
        String accountName = request.getAccountName();
        String password = request.getPassword();
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String role = user.getRole().name();

        String accessToken = jwtUtil.generateAccessToken(accountName,role, user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(accountName,role, user.getId());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }
}
