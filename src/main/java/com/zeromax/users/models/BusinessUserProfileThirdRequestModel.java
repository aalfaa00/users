package com.zeromax.users.models;

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
public class BusinessUserProfileThirdRequestModel {

    private List<PaymentOptions> paymentOptions;
}
