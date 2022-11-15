package com.zeromax.users.service;

import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.entity.user.UserType;
import com.zeromax.users.exeptions.NotFoundRequestException;
import com.zeromax.users.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    CreateUserResponseModel createUser(CreateUserModel createUserModel);

    void createEmployee(EmployeeRegistrationRequestModel model, String userId);

    EmployeeRegistrationResponseModel employeeCompleteRegistration(EmployeeCompleteRegistrationRequestModel employee, String token);

    Page<EmployeeProfileResponseModel> getPaginatedEmployeesWithSorting(Pageable page, String companyId, String name, String sortBy);

    UserEntity getUserByEmail(String email) throws NotFoundRequestException;

    UserEntity getUserById(String user_id) throws NotFoundRequestException;

    UserType getUserType(String user_id) throws NotFoundRequestException;

    void logout(String user_id);

    LoginResponseModel refreshToken(String refresh_token);

    void updatePassword(UpdatePasswordRequestModel model, String user_id);

    void sendLinkToResetPassword(String email);

    void resetPassword(String token, String password);

    void deleteUser(String userId);

    SimpleUserProfileResponseModel fillSimpleUserProfileInfo(SimpleUserProfileInfoModel simpleUserProfileInfoModel,
                                                             String user_id);

    SimpleUserProfileResponseModel getSimpleUserProfile(String userId);

    CommercialUserProfileResponseModel fillCommercialUserProfileFirstStep(CommercialUserProfileFirstRequestModel model,
                                                                          String user_id);

    CommercialUserProfileResponseModel fillCommercialUserProfileSecondStep(CommercialUserProfilesSecondRequestModel model,
                                                                           String user_id);

    EmployeeProfileResponseModel getEmployeeProfile(String companyId, String userId);

    EmployeeProfileResponseModel changeEmployeeProfile(EmployeeProfileRequestModel model, String userId);

    void sendVerificationLinkToEmail(String userId);

    void sendVerificationNumbersToPhone(String userId);

    void verifyEmailByToken(String token);

    void verifyPhoneByNumbers(String token);
}
