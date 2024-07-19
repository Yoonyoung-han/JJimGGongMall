package com.kinzie.productservice.client;

import com.kinzie.productservice.common.dto.response.AddressResponseDto;
import com.kinzie.productservice.common.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/internal/users/{userId}")
    UserResponseDto getUserById(@PathVariable Long userId);

    @GetMapping("/api/internal/address/{addressId}")
    AddressResponseDto getAddressById(@PathVariable Long addressId);

}
