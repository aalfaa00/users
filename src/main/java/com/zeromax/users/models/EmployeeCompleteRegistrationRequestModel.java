package com.zeromax.users.models;

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
public class EmployeeCompleteRegistrationRequestModel {

    @NotNull(message = "first name is required")
    private String firstName;
    @NotNull(message = "last Name is required")
    private String lastName;
    @NotNull(message = "password is required")
    @Pattern(regexp = "^(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*\\d)(?=\\S*[^\\w\\s])\\S{8,}$",
            message = "Password should contain 1 capital, a digit, a special character")
    private String password;
}
