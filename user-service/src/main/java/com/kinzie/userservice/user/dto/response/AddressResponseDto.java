package com.kinzie.userservice.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AddressResponseDto {
    private Long addressId;
    private String alias;
    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Boolean isDefault;

    @Builder
    public AddressResponseDto(Long addressId, String alias, String recipientName, String addressLine1,
                              String addressLine2, String city, String state,
                              String postalCode, String country, Boolean isDefault) {
        this.addressId = addressId;
        this.alias = alias;
        this.recipientName = recipientName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.isDefault = isDefault;
    }
}
