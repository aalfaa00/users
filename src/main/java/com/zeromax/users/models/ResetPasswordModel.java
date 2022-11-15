package com.zeromax.users.models;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class ResetPasswordModel {

    @NotNull(message = "password is required")
    @Pattern(regexp = "^(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*\\d)(?=\\S*[^\\w\\s])\\S{8,}$",
            message = "Password should contain 1 capital, a digit, a special character")
    private String password;
}
