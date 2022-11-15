package com.zeromax.users.service;

import com.zeromax.users.utils.Constants;
import com.zeromax.users.entity.VerificationEntity;
import com.zeromax.users.entity.VerificationType;
import com.zeromax.users.exeptions.NotFoundRequestException;
import com.zeromax.users.repository.VerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;

    public VerificationService(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    private VerificationEntity findByToken(String token){
        var entity = verificationRepository.findByToken(token);
        if (entity.isEmpty()){
            throw new NotFoundRequestException();
        }
        return entity.get();
    }

    public String getVerificationToken(String userId, VerificationType type){
        var entity = verificationRepository.findByUserId(userId);
        String token;
        if(entity.isPresent()){
            token = entity.get().getToken();
        }else {
            VerificationEntity tokenEntity = new VerificationEntity(userId, type);
            token = tokenEntity.getToken();
            verificationRepository.save(tokenEntity);
        }
        return token;
    }

    public String getVerificationLink(String userId, VerificationType type){
        var token = getVerificationToken(userId, type);
        return String.format("%s/verify/%s/%s", Constants.DIRECT_LINK, type.toString().toLowerCase(), token);
    }

    public String verifyToken(String token){
        var verificationEntity = findByToken(token);
        verificationRepository.deleteByToken(verificationEntity.getToken());
        return verificationEntity.getUserId();
    }

    public String getVerificationNumbers(String userId, VerificationType type) {
        var entity = verificationRepository.findByUserId(userId);
        if (entity.isPresent()){
            return entity.get().getToken();
        }
        var verificationEntity = new VerificationEntity(userId, type);
        verificationRepository.save(verificationEntity);
        return verificationEntity.getToken();
    }
}
