package com.kinzie.userservice.user.controller;

import com.kinzie.userservice.common.ApiResponse;
import com.kinzie.userservice.common.security.annotation.AuthUserId;
import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.user.dto.request.CheckDuplicateRequestDto;
import com.kinzie.userservice.user.dto.request.UpdateUserInfoRequestDto;
import com.kinzie.userservice.user.dto.response.UserResponseDto;
import com.kinzie.userservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Environment env;

    @Autowired
    public UserController(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }


    @GetMapping("/health_check")
    public String status(HttpServletRequest request){
        return String.format("It's Working in User Service "+ request.getServerPort() +
        ", token key = "+ env.getProperty("jwt.secret.key") +
        ", tonken access-token-expiration=" + env.getProperty("jwt.access-token-expiration"));
    }

    @GetMapping
    public ApiResponse<List<UserResponseDto>> getUserInfo(){
        Iterable<User> userList = userService.getUserByAll();
        List<UserResponseDto> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, UserResponseDto.class));
        });
        return ApiResponse.ok(result);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUserInfo(@PathVariable Long userId){
        UserResponseDto result = userService.getUserInfo(userId);
        return ApiResponse.ok(result);
    }

    @GetMapping("/check-validate")
    public ApiResponse<String> isDuplicate(@RequestBody CheckDuplicateRequestDto request){
        boolean result = userService.isDuplicate(request);
        return ApiResponse.ok(result? "이미 있는 값 입니다.":"사용할 수 있는 값입니다.");
    }


    @PutMapping
    public ApiResponse<UserResponseDto> updateUserInfo(@AuthUserId Long userId,
                                                       @RequestBody UpdateUserInfoRequestDto request){
        UserResponseDto result = userService.updateUserInfo(userId,request);
        return ApiResponse.ok(result);
    }

}
