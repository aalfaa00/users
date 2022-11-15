package com.zeromax.users.models;

import com.zeromax.users.entity.Address;
import com.zeromax.users.entity.company.PaymentOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUserProfileResponseModel {

    private String id;
    private String companyName;
    private Integer companyTin;
    private String email;
    private String mainAddressId;
    private List<Address> addresses;
    private List<String> categoryIds;
    private List<PaymentOptions> paymentOptions;
    private String pictureUrl;
    private Boolean isActive;
    private Boolean isFinished;
}
