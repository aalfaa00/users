package com.zeromax.users.models;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordResetRequestModel {

    @Email
    @NotNull(message = "email is required")
    private String email;
}
