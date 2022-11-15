package com.zeromax.users.models;

import com.zeromax.users.utils.ConfirmablePassword;
import com.zeromax.users.utils.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordsEqualConstraint(message = "Password and password confirmation does not match, please check and try again")
public class UpdatePasswordRequestModel implements ConfirmablePassword {
    @NotNull(message = "old password is required")
    private String oldPassword;
    @Pattern(regexp = "^(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*\\d)(?=\\S*[^\\w\\s])\\S{8,}$",
            message = "Password should contain 1 capital, a digit, a special character")
    private String password;
    private String confirmPassword;
}
