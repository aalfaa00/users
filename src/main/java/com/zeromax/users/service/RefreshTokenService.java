package com.zeromax.users.service;

import com.zeromax.users.entity.RefreshTokenEntity;
import com.zeromax.users.exeptions.CustomGeneralException;
import com.zeromax.users.exeptions.NotFoundRequestException;
import com.zeromax.users.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshTokenEntity saveToken(String userId, String token) {
        try {
            RefreshTokenEntity refresh = new RefreshTokenEntity();
            refresh.setToken(token);
            refresh.setUserId(userId);
            refresh.setCreatedAt(LocalDateTime.now());
            return refreshTokenRepository.save(refresh);
        } catch (Exception e) {
            throw new CustomGeneralException("Could not read refresh token record", "0000");
        }
    }

    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public Optional<RefreshTokenEntity> getToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void checkAndDeleteRefreshToken(String userId){
        var entity = refreshTokenRepository.findByUserId(userId);
        entity.ifPresent(refreshTokenEntity -> deleteToken(refreshTokenEntity.getToken()));
    }
}

