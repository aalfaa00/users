package com.zeromax.users.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "verificationTokens")
public class VerificationEntity {

    @Id
    private String token;
    @Indexed(unique = true)
    private String userId;
    private LocalDateTime createdAt;
    private Boolean isExpired;


    public VerificationEntity(String userId, VerificationType type){
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        if(type == VerificationType.EMAIL){
            this.token = getToken(userId);
        } else if (type == VerificationType.PHONE) {
            this.token = getVerificationNumbers();
        }
        this.isExpired = false;
    }

    private static final Base64.Encoder encoder = Base64.getUrlEncoder();


    private static String getToken(String userId) {
        var randomToken = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        byte[] byteVersion = (userId + ":" + randomToken).getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(byteVersion);
    }

    private static String getVerificationNumbers(){
        var random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

}
