package com.zeromax.users.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginClaimModel {
    private String id;
    private String email;
    private String userType;
}
