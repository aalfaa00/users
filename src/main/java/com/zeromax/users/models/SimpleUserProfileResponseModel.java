package com.zeromax.users.models;

import com.zeromax.users.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserProfileResponseModel {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String pictureUrl;
    private Address address;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private Boolean isFinished;
}
