package com.kinzie.userservice.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String password;
    private String accountName;
}
