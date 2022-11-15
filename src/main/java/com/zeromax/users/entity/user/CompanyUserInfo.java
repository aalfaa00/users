package com.zeromax.users.entity.user;

import com.zeromax.users.entity.company.CompanyUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUserInfo {

    private String companyId;
    private CompanyUserRole role;
}
