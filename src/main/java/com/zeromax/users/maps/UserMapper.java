package com.zeromax.users.maps;

import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.models.*;
import org.mapstruct.Mapper;

@Mapper()
public interface UserMapper {

    UserEntity mapUserCreateModelToUserEntity(CreateUserModel user);

    CreateUserResponseModel mapUserEntityToCreateUserResponseModel(UserEntity userEntity, String refreshToken, String accessToken);

    UserEntity mapCreateEmployeeUserModelToUserEntity(EmployeeRegistrationRequestModel model);

    EmployeeProfileResponseModel mapUserEntityToEmployeeProfileResponseModel(UserEntity userEntity);

    UserEntity mapSimpleUserProfileInfoModelWithUserEntity(SimpleUserProfileInfoModel model,
                                                                           UserEntity userEntity);

    SimpleUserProfileResponseModel mapUserEntityToSimpleUserProfileResponseModel(UserEntity userEntity);

    UserEntity mapCommercialProfileFirstStepToUserEntity(CommercialUserProfileFirstRequestModel model,
                                                         UserEntity user);

    UserEntity mapCommercialProfileSecondStepToUserEntity(CommercialUserProfilesSecondRequestModel model,
                                                          UserEntity user);

    CommercialUserProfileResponseModel mapUserEntityToCommercialUserProfileResponse(UserEntity user);

    UserEntity mapEmployeeProfileRequestModelToUserEntity(UserEntity userEntity, EmployeeProfileRequestModel model);

    EmployeeRegistrationResponseModel mapUserEntityToEmployeeRegistrationResponseModel(UserEntity userEntity,
                                                                                       String accessToken,
                                                                                       String refreshToken);
}
