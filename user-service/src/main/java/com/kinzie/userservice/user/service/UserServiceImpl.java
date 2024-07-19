package com.kinzie.userservice.user.service;

import com.kinzie.userservice.user.domain.User;
import com.kinzie.userservice.user.domain.constant.Role;
import com.kinzie.userservice.user.dto.UserDto;
import com.kinzie.userservice.user.dto.request.CheckDuplicateRequestDto;
import com.kinzie.userservice.user.dto.request.UpdateUserInfoRequestDto;
import com.kinzie.userservice.user.dto.response.UserResponseDto;
import com.kinzie.userservice.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserDetailsByUsername(String username) {
        User user = userRepository.findByAccountName(username)
                .orElseThrow(() -> new IllegalArgumentException(username));

        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }

    @Override
    public Iterable<User> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found " + userId));
        return UserResponseDto.of(user);
    }

    @Override
    @Transactional
    public boolean isDuplicate(CheckDuplicateRequestDto request) {
        String target = request.getValue();
        Optional<User> userOptional;
        if (request.getCheckType().equals("Email")){
            userOptional = userRepository.findByEmail(target);
        }else {
            userOptional = userRepository.findByAccountName(target);
        }
        return userOptional.isPresent();
    }

    @Override
    @Transactional
    public UserResponseDto updateUserInfo(Long userId, UpdateUserInfoRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Not Found " + userId));
        user.setBirthday(request.getBirthday());
        user.setGender(request.getGender());
        try {
            Role role = Role.valueOf(request.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + request.getRole());
        }

        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());

        User saved = userRepository.save(user);
        return UserResponseDto.of(saved);
    }


}
