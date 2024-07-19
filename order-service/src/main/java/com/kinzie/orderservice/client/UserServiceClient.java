package com.kinzie.orderservice.client;

import com.kinzie.orderservice.common.dto.response.AddressResponseDto;
import com.kinzie.orderservice.common.dto.response.UserResponseDto;
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
