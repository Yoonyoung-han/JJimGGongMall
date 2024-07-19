package com.kinzie.userservice.user.service;

import com.kinzie.userservice.user.dto.response.AddressResponseDto;

public interface AddressService {
    AddressResponseDto getAddressById(Long addressId);
}
