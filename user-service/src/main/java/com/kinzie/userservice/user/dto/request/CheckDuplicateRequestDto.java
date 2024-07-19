package com.kinzie.userservice.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckDuplicateRequestDto {
    private String checkType;
    private String value;
}
