package com.kinzie.userservice.auth.dto.request;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignUpRequestDto {
    private String email;
    private String accountName;
    private String password;
    private String username;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private AddressInfo addressInfo = new AddressInfo(); // 주소 정보 초기화

    @Data
    public static class AddressInfo {
        private String alias;
        private String recipientName;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private boolean isDefault;
    }

    public void encodingPassword(PasswordEncoder passwordEncoder){
        if (password.isEmpty()){
            return;
        }
        this.password = passwordEncoder.encode(password);
    }
}
