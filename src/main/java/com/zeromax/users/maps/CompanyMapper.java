package com.zeromax.users.maps;

import com.zeromax.users.entity.company.CompanyEntity;
import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.models.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper()
public interface CompanyMapper {

    CompanyEntity mapBusinessProfileFirstModelWithCompanyEntity(BusinessUserProfileFirstRequestModel model,
                                                                CompanyEntity company);

    CompanyEntity mapBusinessProfileSecondModelWithCompanyEntity(BusinessUserProfileSecondRequestModel model,
                                                                 CompanyEntity company);

    CompanyEntity mapBusinessProfileThirdModelWithCompanyEntity(BusinessUserProfileThirdRequestModel model,
                                                                CompanyEntity company);

    BusinessUserProfileResponseModel mapCompanyEntityToBusinessProfileResponseModel(CompanyEntity companyEntity);

//    Page<EmployeesTableResponseModel> mapPageableUserEntityToPageableEmployeeSTableModel(Page<UserEntity> userEntities);
}
