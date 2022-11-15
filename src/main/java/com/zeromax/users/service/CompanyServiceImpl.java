package com.zeromax.users.service;

import com.zeromax.users.entity.company.CompanyEntity;
import com.zeromax.users.exeptions.InvalidRequestException;
import com.zeromax.users.exeptions.NotFoundRequestException;
import com.zeromax.users.maps.CompanyMapper;
import com.zeromax.users.models.*;
import com.zeromax.users.repository.CompanyRepository;
import com.zeromax.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.companyMapper = companyMapper;
    }

    private String getCompanyId(String userId){
        var user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getCompanyUserInfo().getCompanyId();
        }
        throw new NotFoundRequestException("No such user", "0000");
    }


    @Override
    public CompanyEntity registerCompany(CompanyEntity companyEntity) {
        var company = companyRepository.findByEmail(companyEntity.getId());
        if (company.isPresent()){
            throw new InvalidRequestException("Email already registered");
        }
        return companyRepository.save(companyEntity);
    }

    @Override
    public void saveCompany(CompanyEntity company) {
        companyRepository.save(company);
    }

    @Override
    public CompanyEntity getCompanyById(String companyId) {
        var company = companyRepository.findById(companyId);
        if (company.isEmpty()){
            throw new NotFoundRequestException("No such company", "0000");
        }
        return company.get();
    }

    @Override
    public BusinessUserProfileResponseModel fillBusinessUserProfileFirstStep(BusinessUserProfileFirstRequestModel model,
                                                                             String userId) {
        var companyId = getCompanyId(userId);
        var company = companyMapper.mapBusinessProfileFirstModelWithCompanyEntity(model, getCompanyById(companyId));
        company.setMainAddressId(company.getAddresses().get(0).getId());
        return companyMapper.mapCompanyEntityToBusinessProfileResponseModel(companyRepository.save(company));
    }

    @Override
    public BusinessUserProfileResponseModel fillBusinessUserProfileSecondStep(BusinessUserProfileSecondRequestModel model,
                                                                              String userId) {
        var companyId = getCompanyId(userId);
        //TODO: check each categoryId
        var company = companyMapper.mapBusinessProfileSecondModelWithCompanyEntity(model, getCompanyById(companyId));
        return companyMapper.mapCompanyEntityToBusinessProfileResponseModel(companyRepository.save(company));
    }

    @Override
    public BusinessUserProfileResponseModel fillBusinessUserProfileThirdStep(BusinessUserProfileThirdRequestModel model,
                                                                             String userId) {
        var companyId = getCompanyId(userId);
        var company = companyMapper.mapBusinessProfileThirdModelWithCompanyEntity(model, getCompanyById(companyId));
        return companyMapper.mapCompanyEntityToBusinessProfileResponseModel(companyRepository.save(company));
    }
}
