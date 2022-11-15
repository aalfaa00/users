package com.zeromax.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileResponseModel {

    private String id;
    private String companyId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private String ssn;
    private String workingLocation;
    private String attachedFileUrl;
    private String pictureUrl;
    private Double rating;
    private LocalDateTime startedDate;
    private List<String> categoryIds;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;
    private Boolean isActive;
}
