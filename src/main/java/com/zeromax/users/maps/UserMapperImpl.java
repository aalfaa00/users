package com.zeromax.users.maps;

import com.zeromax.users.entity.Address;
import com.zeromax.users.entity.Contact;
import com.zeromax.users.entity.user.EmployeeInfo;
import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.models.*;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.UUID;


@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper{

    @Override
    public UserEntity mapUserCreateModelToUserEntity(CreateUserModel user){
        if (user == null){
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setUserType(user.getUserType());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }

    @Override
    public CreateUserResponseModel mapUserEntityToCreateUserResponseModel(UserEntity userEntity, String refreshToken, String accessToken) {
        if (userEntity == null){
            return null;
        }
        CreateUserResponseModel userResponseModel = new CreateUserResponseModel();
        userResponseModel.setId(userEntity.getId());
        userResponseModel.setEmail(userEntity.getEmail());
        userResponseModel.setUserType(userEntity.getUserType());
        userResponseModel.setIsActive(userEntity.getIsActive());
        userResponseModel.setIsFinished(userEntity.getIsFinished());
        userResponseModel.setAccessToken(accessToken);
        userResponseModel.setRefreshToken(refreshToken);
        return userResponseModel;
    }

    @Override
    public UserEntity mapCreateEmployeeUserModelToUserEntity(EmployeeRegistrationRequestModel model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(model.getEmail());

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setPhone(model.getPhone());
        employeeInfo.setSsn(model.getSsn());
        employeeInfo.setCompanyId(model.getCompanyId());
        employeeInfo.setOfficeId(model.getWorkingLocation());
        employeeInfo.setAttachedFileUrl(model.getAttachedFileUrl());
        userEntity.setEmployeeInfo(employeeInfo);
        return userEntity;
    }

    @Override
    public EmployeeProfileResponseModel mapUserEntityToEmployeeProfileResponseModel(UserEntity userEntity) {
        EmployeeProfileResponseModel model = new EmployeeProfileResponseModel();
        model.setId(userEntity.getId());
        model.setCompanyId(userEntity.getEmployeeInfo().getCompanyId());
        model.setEmail(userEntity.getEmail());
        model.setFirstName(userEntity.getFirstName());
        model.setLastName(userEntity.getLastName());
        model.setDateOfBirth(userEntity.getDateOfBirth());
        model.setPhone(userEntity.getEmployeeInfo().getPhone());
        model.setSsn(userEntity.getEmployeeInfo().getSsn());
        model.setWorkingLocation(userEntity.getEmployeeInfo().getOfficeId());
        model.setAttachedFileUrl(userEntity.getEmployeeInfo().getAttachedFileUrl());
        model.setPictureUrl(userEntity.getPictureUrl());
        model.setRating(userEntity.getEmployeeInfo().getRating());
        model.setStartedDate(userEntity.getEmployeeInfo().getStartedDate());
        model.setCategoryIds(userEntity.getEmployeeInfo().getCategoryIds());
        model.setIsEmailVerified(userEntity.getIsEmailVerified());
        model.setIsPhoneVerified(userEntity.getEmployeeInfo().getIsPhoneVerified());
        model.setIsActive(userEntity.getIsActive());
        return model;
    }

    @Override
    public UserEntity mapSimpleUserProfileInfoModelWithUserEntity(SimpleUserProfileInfoModel model,
                                                                                  UserEntity userEntity) {
        if(model == null || userEntity == null){
            return null;
        }
        userEntity.setFirstName(model.getFirstName());
        userEntity.setLastName(model.getLastName());
        userEntity.setDateOfBirth(model.getDateOfBirth());

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setAddress(model.getAddress());
        address.setState(model.getState());
        address.setCity(model.getCity());
        address.setZipCode(model.getZipCode());
        address.setIsMain(true);

        Contact contact = new Contact();
        contact.setPhone(model.getPhone());
        address.setContact(contact);
        userEntity.setAddresses(List.of(address));
        return userEntity;
    }

    @Override
    public SimpleUserProfileResponseModel mapUserEntityToSimpleUserProfileResponseModel(UserEntity userEntity) {
        if(userEntity == null){
            return null;
        }
        SimpleUserProfileResponseModel model = new SimpleUserProfileResponseModel();
        model.setFirstName(userEntity.getFirstName());
        model.setLastName(userEntity.getLastName());
        model.setEmail(userEntity.getEmail());
        model.setId(userEntity.getId());
        model.setDateOfBirth(userEntity.getDateOfBirth());
        model.setPictureUrl(userEntity.getPictureUrl());
        model.setAddress(userEntity.getAddresses().get(0));
        model.setIsFinished(userEntity.getIsFinished());
        model.setIsEmailVerified(userEntity.getIsEmailVerified());
        model.setIsActive(userEntity.getIsActive());
        return model;
    }

    @Override
    public UserEntity mapCommercialProfileFirstStepToUserEntity(CommercialUserProfileFirstRequestModel model,
                                                                UserEntity user) {
        user.getCommercialInfo().setCompanyName(model.getCompanyName());
        user.getCommercialInfo().setCompanyTin(model.getCompanyTin());

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setAddress(model.getAddress());
        address.setState(model.getState());
        address.setCity(model.getCity());
        address.setZipCode(model.getZipCode());
        address.setIsMain(true);

        Contact contact = new Contact();
        contact.setPhone(model.getPhone());
        address.setContact(contact);

        user.setAddresses(List.of(address));
        return user;
    }

    @Override
    public UserEntity mapCommercialProfileSecondStepToUserEntity(CommercialUserProfilesSecondRequestModel model,
                                                                 UserEntity user) {
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.getCommercialInfo().setBusinessType(model.getBusinessType());
        user.getCommercialInfo().setPosition(model.getPosition());
        return user;
    }

    @Override
    public CommercialUserProfileResponseModel mapUserEntityToCommercialUserProfileResponse(UserEntity user){
        CommercialUserProfileResponseModel model = new CommercialUserProfileResponseModel();
        model.setId(user.getId());
        model.setEmail(user.getEmail());
        model.setCompanyName(user.getCommercialInfo().getCompanyName());
        model.setCompanyTin(user.getCommercialInfo().getCompanyTin());
        model.setBusinessType(user.getCommercialInfo().getBusinessType());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setPosition(user.getCommercialInfo().getPosition());
        model.setPictureUrl(user.getPictureUrl());
        model.setAddresses(user.getAddresses());
        model.setIsActive(user.getIsActive());
        model.setIsFinished(user.getIsFinished());
        return model;
    }

    @Override
    public UserEntity mapEmployeeProfileRequestModelToUserEntity(UserEntity userEntity, EmployeeProfileRequestModel model) {
        userEntity.setFirstName(model.getFirstName());
        userEntity.setLastName(model.getLastName());
        userEntity.setDateOfBirth(model.getDateOfBirth());
        userEntity.getEmployeeInfo().setPhone(model.getPhone());
        return userEntity;
    }

    @Override
    public EmployeeRegistrationResponseModel mapUserEntityToEmployeeRegistrationResponseModel(UserEntity userEntity,
                                                                                              String accessToken,
                                                                                              String refreshToken) {
        EmployeeRegistrationResponseModel model = new EmployeeRegistrationResponseModel();
        model.setFirstName(userEntity.getFirstName());
        model.setLastName(userEntity.getLastName());
        model.setEmail(userEntity.getEmail());
        model.setPhone(userEntity.getEmployeeInfo().getPhone());
        model.setAccessToken(accessToken);
        model.setRefreshToken(refreshToken);
        return model;
    }


}
