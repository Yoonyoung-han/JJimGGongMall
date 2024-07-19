package com.kinzie.userservice.user.service;

import com.kinzie.userservice.common.config.ModelMapperConfig;
import com.kinzie.userservice.user.domain.Address;
import com.kinzie.userservice.user.dto.response.AddressResponseDto;
import com.kinzie.userservice.user.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressResponseDto getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ addressId));
        return new ModelMapper().map(address, AddressResponseDto.class);
    }
}
