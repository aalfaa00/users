package com.zeromax.users.service;

import com.zeromax.users.entity.company.CompanyEntity;
import com.zeromax.users.models.*;

public interface CompanyService {

    CompanyEntity registerCompany(CompanyEntity company);

    void saveCompany(CompanyEntity company);

    CompanyEntity getCompanyById(String companyId);

    BusinessUserProfileResponseModel fillBusinessUserProfileFirstStep(BusinessUserProfileFirstRequestModel model,
                                                                      String userId);

    BusinessUserProfileResponseModel fillBusinessUserProfileSecondStep(BusinessUserProfileSecondRequestModel model,
                                                                       String userId);

    BusinessUserProfileResponseModel fillBusinessUserProfileThirdStep(BusinessUserProfileThirdRequestModel model,
                                                                      String userId);
}
