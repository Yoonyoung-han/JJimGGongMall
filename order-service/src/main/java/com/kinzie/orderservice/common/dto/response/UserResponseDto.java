package com.kinzie.orderservice.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String accountName;
    private String username;
    private String phoneNumber;
    private String birthday;
    private String email;
    private String gender;

    @Builder
    public UserResponseDto(Long userId, String accountName, String username, String phoneNumber, String birthday, String email, String gender) {
        this.userId = userId;
        this.accountName = accountName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
    }

}
