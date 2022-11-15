package com.zeromax.users.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommercialInfo {

    private String companyName;
    private String companyTin;
    private String position;
    private String businessType;
}
