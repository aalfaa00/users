package com.zeromax.users.repository;

import com.zeromax.users.entity.company.CompanyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {

    Optional<CompanyEntity> findByEmail(String email);
}
