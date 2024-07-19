package com.kinzie.userservice.user.repository;

import com.kinzie.userservice.user.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
