package com.zeromax.users.models;

import com.zeromax.users.entity.user.UserType;
import com.zeromax.users.utils.ConfirmablePassword;
import com.zeromax.users.utils.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordsEqualConstraint(message = "Password and password confirmation does not match, please check and try again")
public class CreateUserModel implements ConfirmablePassword {

    @Id
    private String id;
    @NotNull(message = "user type is required")
    private UserType userType;
    @NotNull(message = "email is required")
    @Email
    @Indexed(unique = true)
    private String email;
    @NotNull(message = "password is required")
    @Pattern(regexp = "^(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*\\d)(?=\\S*[^\\w\\s])\\S{8,}$",
            message = "Password should contain 1 capital, a digit, a special character")
    private String password;
    private String confirmPassword;
}
