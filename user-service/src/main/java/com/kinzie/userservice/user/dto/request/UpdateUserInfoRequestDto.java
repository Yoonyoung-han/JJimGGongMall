package com.kinzie.userservice.user.dto.request;

import lombok.Data;

@Data
public class UpdateUserInfoRequestDto {
    private Long userId;
    private String accountName;
    private String username;
    private String phoneNumber;
    private String birthday;
    private String email;
    private String gender;
    private String password;
    private String role;
}
