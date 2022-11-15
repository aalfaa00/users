package com.zeromax.users.repository;

import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.entity.user.UserType;
import com.zeromax.users.models.EmployeeProfileResponseModel;
import org.springframework.data.domain.Page;

public interface CustomUsersFilter {

    Page<EmployeeProfileResponseModel> getFilteredAndOrderedEmployees(Integer page, Integer size, String companyId, UserType userType,
                                                                      String keyword, String sortBy);
}
