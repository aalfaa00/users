package com.zeromax.users.models;

import com.zeromax.users.entity.Address;
import com.zeromax.users.entity.user.CommercialInfo;
import com.zeromax.users.entity.user.CompanyUserInfo;
import com.zeromax.users.entity.user.EmployeeInfo;
import com.zeromax.users.entity.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseModel {

    @Id
    private String id;
    private String email;
    private UserType userType;
    private Boolean isActive;
    private Boolean isFinished;

// Access credentials
    private String accessToken;
    private String refreshToken;
}
