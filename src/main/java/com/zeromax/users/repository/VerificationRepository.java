package com.zeromax.users.repository;

import com.zeromax.users.entity.VerificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationRepository extends CrudRepository<VerificationEntity, String> {

    void deleteByToken(String token);

    Optional<VerificationEntity> findByToken(String token);

    Optional<VerificationEntity> findByUserId(String userId);
}
