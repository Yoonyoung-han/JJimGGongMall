package com.kinzie.userservice.user.controller.internal;

import com.kinzie.userservice.user.dto.response.AddressResponseDto;
import com.kinzie.userservice.user.dto.response.UserResponseDto;
import com.kinzie.userservice.user.service.AddressService;
import com.kinzie.userservice.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/internal")
public class InternalController {

    private final UserService userService;
    private final AddressService addressService;
    private final Environment env;

    @Autowired
    public InternalController(UserService userService, AddressService addressService,
                              Environment env) {
        this.userService = userService;
        this.addressService = addressService;
        this.env = env;
    }

    @GetMapping("/address/{addressId}")
    public AddressResponseDto getAddressById(@PathVariable Long addressId){
        return addressService.getAddressById(addressId);
    }

    @GetMapping("/users/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId){
        return userService.getUserInfo(userId);
    }
}
