package com.kinzie.userservice.user.service;

import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.user.dto.UserDto;
import com.kinzie.userservice.user.dto.request.CheckDuplicateRequestDto;
import com.kinzie.userservice.user.dto.request.UpdateUserInfoRequestDto;
import com.kinzie.userservice.user.dto.response.UserResponseDto;

public interface UserService {

    UserDto getUserDetailsByUsername(String username);

    Iterable<User> getUserByAll();

    UserResponseDto getUserInfo(Long userId);

    boolean isDuplicate(CheckDuplicateRequestDto request);

    UserResponseDto updateUserInfo(Long userId, UpdateUserInfoRequestDto request);
}
