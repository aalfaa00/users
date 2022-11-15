package com.zeromax.users.models;

import com.zeromax.users.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommercialUserProfileResponseModel {

    private String id;
    private String email;
    private String companyName;
    private String companyTin;
    private String businessType;
    private String firstName;
    private String lastName;
    private String position;
    private String pictureUrl;
    private List<Address> addresses;
    private Boolean isActive;
    private Boolean isFinished;


}
