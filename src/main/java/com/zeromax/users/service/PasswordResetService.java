package com.zeromax.users.service;

import com.zeromax.users.utils.Constants;
import com.zeromax.users.entity.VerificationType;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final VerificationService verificationTokenService;

    public PasswordResetService(VerificationService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    public String getLinkToResetPassword(String userId){
        var token = verificationTokenService.getVerificationToken(userId, VerificationType.EMAIL);
        return String.format("%s/password/reset/%s", Constants.DIRECT_LINK, token);
    }
}
