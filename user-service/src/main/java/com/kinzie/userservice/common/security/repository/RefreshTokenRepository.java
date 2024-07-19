package com.kinzie.userservice.common.security.repository;

import com.kinzie.userservice.common.security.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
