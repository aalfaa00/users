package com.zeromax.users.repository;

import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.entity.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String>, PagingAndSortingRepository<UserEntity, String>,
CustomUsersFilter{

    Optional<UserEntity> findByEmail(String email);
}