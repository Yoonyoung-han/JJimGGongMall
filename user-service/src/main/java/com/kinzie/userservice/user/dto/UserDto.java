package com.kinzie.userservice.user.dto;

import lombok.Data;

@Data
public class UserDto {
    String username;
    String role;
    String email;
    String accountName;
}
