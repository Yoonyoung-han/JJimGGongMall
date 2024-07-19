package com.kinzie.userservice.user.dto.response;

import com.kinzie.userservice.user.domain.User;
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

    public static UserResponseDto of(User user){
        return UserResponseDto.builder()
                .accountName(user.getAccountName())
                .birthday(user.getBirthday())
                .userId(user.getId())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
