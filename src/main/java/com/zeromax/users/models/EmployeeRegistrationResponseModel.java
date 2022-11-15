package com.zeromax.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationResponseModel {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Access credentials
    private String accessToken;
    private String refreshToken;
}
