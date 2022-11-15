package com.zeromax.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesTableResponseModel {

    private String photo;
    private String name;
    private String id;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private Boolean status;
}
