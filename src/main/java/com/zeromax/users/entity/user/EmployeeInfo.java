package com.zeromax.users.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {

    private String ssn;
    private String phone;
    private String companyId;
    private String officeId;
    private List<String> categoryIds;
    private Double rating = 0.00;
    private String attachedFileUrl;
    private LocalDateTime startedDate = LocalDateTime.now();
    private Boolean IsPhoneVerified = false;
}
