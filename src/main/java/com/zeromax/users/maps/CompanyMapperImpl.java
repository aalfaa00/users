package com.zeromax.users.maps;

import com.zeromax.users.entity.Address;
import com.zeromax.users.entity.Contact;
import com.zeromax.users.entity.company.CompanyEntity;
import com.zeromax.users.models.BusinessUserProfileFirstRequestModel;
import com.zeromax.users.models.BusinessUserProfileResponseModel;
import com.zeromax.users.models.BusinessUserProfileSecondRequestModel;
import com.zeromax.users.models.BusinessUserProfileThirdRequestModel;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.UUID;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper{

    @Override
    public CompanyEntity mapBusinessProfileFirstModelWithCompanyEntity(BusinessUserProfileFirstRequestModel model,
                                                                       CompanyEntity company) {
        company.setCompanyName(model.getCompanyName());
        company.setCompanyTin(model.getCompanyTin());

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

        company.setMainAddressId(address.getId());
        company.setAddresses(List.of(address));
        return company;
    }

    @Override
    public CompanyEntity mapBusinessProfileSecondModelWithCompanyEntity(BusinessUserProfileSecondRequestModel model,
                                                                        CompanyEntity company) {
        company.setCategoryIds(model.getCategoryIds());
        return company;
    }

    @Override
    public CompanyEntity mapBusinessProfileThirdModelWithCompanyEntity(BusinessUserProfileThirdRequestModel model,
                                                                       CompanyEntity company) {
        company.setPaymentOptions(model.getPaymentOptions());
        return company;
    }

    @Override
    public BusinessUserProfileResponseModel mapCompanyEntityToBusinessProfileResponseModel(CompanyEntity companyEntity) {

        BusinessUserProfileResponseModel model = new BusinessUserProfileResponseModel();
        model.setId(companyEntity.getId());
        model.setCompanyName(companyEntity.getCompanyName());
        model.setCompanyTin(companyEntity.getCompanyTin());
        model.setEmail(companyEntity.getEmail());
        model.setMainAddressId(companyEntity.getMainAddressId());
        model.setAddresses(companyEntity.getAddresses());
        model.setCategoryIds(companyEntity.getCategoryIds());
        model.setPaymentOptions(companyEntity.getPaymentOptions());
        model.setPictureUrl(companyEntity.getPictureUrl());
        model.setIsActive(companyEntity.getIsActive());
        model.setIsFinished(companyEntity.getIsFinished());
        return model;
    }
}
