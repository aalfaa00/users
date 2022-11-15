package com.zeromax.users.service;

import com.zeromax.users.entity.Address;
import com.zeromax.users.entity.VerificationType;
import com.zeromax.users.entity.company.CompanyEntity;
import com.zeromax.users.entity.company.CompanyUserRole;
import com.zeromax.users.entity.user.CompanyUserInfo;
import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.entity.user.UserType;
import com.zeromax.users.exeptions.CustomGeneralException;
import com.zeromax.users.exeptions.InvalidRequestException;
import com.zeromax.users.exeptions.NotFoundRequestException;
import com.zeromax.users.exeptions.UnauthorizedRequestException;
import com.zeromax.users.maps.UserMapper;
import com.zeromax.users.models.*;
import com.zeromax.users.repository.UserRepository;
import com.zeromax.users.utils.Constants;
import com.zeromax.users.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private final CompanyService companyService;
    private final EmailSenderService emailSenderService;
    private final VerificationService verificationService;
    private final PhoneSenderService phoneSenderService;
    private final PasswordResetService passwordResetService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper,
                           RefreshTokenService refreshTokenService, CompanyService companyService,
                           EmailSenderService emailSenderService, VerificationService verificationService,
                           PhoneSenderService phoneSenderService, PasswordResetService passwordResetService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.refreshTokenService = refreshTokenService;
        this.companyService = companyService;
        this.emailSenderService = emailSenderService;
        this.verificationService = verificationService;
        this.phoneSenderService = phoneSenderService;
        this.passwordResetService = passwordResetService;
    }

    @Override
    public CreateUserResponseModel createUser(CreateUserModel user) throws InvalidRequestException {
        var opt = userRepository.findByEmail(user.getEmail());
        if (opt.isPresent()){
            throw new InvalidRequestException("Email already registered", "0000");
        }
        if (user.getUserType() == UserType.EMPLOYEE){
            throw new InvalidRequestException();
        }
        UserEntity userEntity = userMapper.mapUserCreateModelToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setIsActive(true);
        userEntity.setIsFinished(false);
        userRepository.save(userEntity);
        if (user.getUserType() == UserType.COMPANY){
            var companyUser = getUserByEmail(user.getEmail());
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setId(companyUser.getId());
            companyEntity.setEmail(companyUser.getEmail());
            companyEntity.setIsActive(companyUser.getIsActive());
            companyEntity.setIsFinished(companyUser.getIsFinished());
            var company = companyService.registerCompany(companyEntity);
            CompanyUserInfo companyUserInfo = new CompanyUserInfo();
            companyUserInfo.setCompanyId(company.getId());
            companyUserInfo.setRole(CompanyUserRole.ADMIN);
            companyUser.setCompanyUserInfo(companyUserInfo);
            userRepository.save(companyUser);
        }
        var userWithId = getUserByEmail(user.getEmail());
        String refreshToken = JWTUtils.createToken(userWithId, Constants.COMPANY_NAME, Constants.REFRESH_KEY, Constants.REFRESH_TTL);
        refreshTokenService.saveToken(userWithId.getId(), refreshToken);
        String accessToken = JWTUtils.createToken(userWithId, Constants.COMPANY_NAME, Constants.ACCESS_KEY, Constants.ACCESS_TTL);
        return userMapper.mapUserEntityToCreateUserResponseModel(userWithId, refreshToken, accessToken);
    }

    @Override
    public void createEmployee(EmployeeRegistrationRequestModel model, String userId) {
        var user = getUserById(userId);
        if(user.getUserType() != UserType.COMPANY){
            throw new InvalidRequestException();
        }
        model.setCompanyId(user.getCompanyUserInfo().getCompanyId());
        UserEntity userEntity = userMapper.mapCreateEmployeeUserModelToUserEntity(model);
        var opt = userRepository.findByEmail(userEntity.getEmail());
        if (opt.isPresent()){
            throw new InvalidRequestException("Email already registered", "0000");
        }
        userEntity.setUserType(UserType.EMPLOYEE);
        userEntity.setIsActive(false);
        userEntity.setIsFinished(false);
        var employee = userRepository.save(userEntity);
        var token = verificationService.getVerificationToken(employee.getId(), VerificationType.EMAIL);
        var link = String.format("%s/employee/register/%s", Constants.DIRECT_LINK, token);
        if(model.getVerificationType() == VerificationType.PHONE){
            phoneSenderService.sendSms(model.getPhone(), link);
        }else {
            emailSenderService.sendMail(model.getEmail(), "Fill profile info", link);
        }
    }

    @Override
    public EmployeeRegistrationResponseModel employeeCompleteRegistration(EmployeeCompleteRegistrationRequestModel employee, String token) {
        var userId = verificationService.verifyToken(token);
        var user = getUserById(userId);
        user.setFirstName(employee.getFirstName());
        user.setLastName(employee.getLastName());
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        user.setIsActive(true);
        user.setIsFinished(true);
        var savedUser = userRepository.save(user);
        String refreshToken = JWTUtils.createToken(savedUser, Constants.COMPANY_NAME, Constants.REFRESH_KEY, Constants.REFRESH_TTL);
        refreshTokenService.saveToken(savedUser.getId(), refreshToken);
        String accessToken = JWTUtils.createToken(savedUser, Constants.COMPANY_NAME, Constants.ACCESS_KEY, Constants.ACCESS_TTL);
        return userMapper.mapUserEntityToEmployeeRegistrationResponseModel(savedUser, accessToken, refreshToken);
    }

    @Override
    public Page<EmployeeProfileResponseModel> getPaginatedEmployeesWithSorting(Pageable page, String companyId, String keyword, String sortBy){
        return userRepository.getFilteredAndOrderedEmployees(page.getPageNumber(),
                page.getPageSize(), companyId, UserType.EMPLOYEE, keyword, sortBy);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            var usr = opt.get();
            return User.builder()
                    .username(usr.getEmail())
                    .password(usr.getPassword())
                    .roles(usr.getUserType().name())
                    .build();
        } else {
            throw new UsernameNotFoundException("Not user with email " + email + " found");
        }
    }

    @Override
    public UserEntity getUserByEmail(String email) throws NotFoundRequestException{
        var user = userRepository.findByEmail(email);
        if (user.isPresent()){
            return user.get();
        }
        throw new NotFoundRequestException("No user with this email", "0000");
    }

    @Override
    public UserEntity getUserById(String userId) throws NotFoundRequestException {
        var user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new NotFoundRequestException("No such user", "0000");
    }

    @Override
    public UserType getUserType(String userId) throws NotFoundRequestException {
        var user = getUserById(userId);
        return user.getUserType();
    }


    @Override
    public void logout(String userId){
        refreshTokenService.checkAndDeleteRefreshToken(userId);
    }

    @Override
    public LoginResponseModel refreshToken(String refreshToken) {
        var refreshRecord = refreshTokenService.getToken(refreshToken);
        if (refreshRecord.isEmpty()) {
            throw new UnauthorizedRequestException(
                    "Token is expired or invalid, please login again to take new refresh token"
            );
        }

        var claims = JWTUtils.getUserDetails(refreshToken, Constants.REFRESH_KEY);

        Optional<UserEntity> optUser = userRepository.findById(claims.getId());
        if (optUser.isPresent()) {
            LoginResponseModel responseModel = new LoginResponseModel();
            String newAccess = JWTUtils.createToken(optUser.get(),
                    Constants.COMPANY_NAME,
                    Constants.ACCESS_KEY,
                    Constants.ACCESS_TTL);
            responseModel.setRefreshToken(refreshToken);
            responseModel.setAccessToken(newAccess);
            return responseModel;
        } else {
            throw new UnauthorizedRequestException();
        }
    }

    @Override
    public void updatePassword(UpdatePasswordRequestModel model, String userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()){
            var user = optionalUser.get();
            if(passwordEncoder.matches(model.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(model.getPassword()));
                userRepository.save(user);
            }
            else {
                throw new InvalidRequestException("Old password is not matching");
            }
        }
        else {
            throw new NotFoundRequestException("No such user", "0000");
        }
    }

    @Override
    public void sendLinkToResetPassword(String email) {
        var user = getUserByEmail(email);
        var userId = user.getId();
        var link = passwordResetService.getLinkToResetPassword(userId);
        emailSenderService.sendMail(email, "Reset Password Link", link);
    }

    @Override
    public void resetPassword(String token, String password) {
        var userId = verificationService.verifyToken(token);
        var user = getUserById(userId);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public SimpleUserProfileResponseModel fillSimpleUserProfileInfo(SimpleUserProfileInfoModel simpleUserProfileInfoModel,
                                                                    String userId) {

        var mappedData = userMapper
                .mapSimpleUserProfileInfoModelWithUserEntity(simpleUserProfileInfoModel, getUserById(userId));
        var userEntity = userRepository.save(mappedData);
        return userMapper.mapUserEntityToSimpleUserProfileResponseModel(userEntity);

    }

    public SimpleUserProfileResponseModel getSimpleUserProfile(String userId){
        var user = getUserById(userId);
        return userMapper.mapUserEntityToSimpleUserProfileResponseModel(user);
    }

    @Override
    public CommercialUserProfileResponseModel fillCommercialUserProfileFirstStep(CommercialUserProfileFirstRequestModel model,
                                                                                 String userId) {
        var mappedData = userMapper.mapCommercialProfileFirstStepToUserEntity(model, getUserById(userId));
        var user = userRepository.save(mappedData);
        return userMapper.mapUserEntityToCommercialUserProfileResponse(user);
    }

    @Override
    public CommercialUserProfileResponseModel fillCommercialUserProfileSecondStep(CommercialUserProfilesSecondRequestModel model,
                                                                                  String userId) {
        var mappedData = userMapper.mapCommercialProfileSecondStepToUserEntity(model, getUserById(userId));
        mappedData.setIsFinished(true);
        var user = userRepository.save(mappedData);
        return userMapper.mapUserEntityToCommercialUserProfileResponse(user);
    }

    @Override
    public EmployeeProfileResponseModel getEmployeeProfile(String companyId, String userId) {
        //TODO: check design employee profile from app and web
        var optEmployee = userRepository.findById(userId);
        if (optEmployee.isEmpty() || optEmployee.get().getUserType() != UserType.EMPLOYEE){
            throw new NotFoundRequestException("No such user", "0000");
        }
        if(companyId == null){
            return userMapper.mapUserEntityToEmployeeProfileResponseModel(optEmployee.get());
        } else if (optEmployee.get().getEmployeeInfo().getCompanyId().equals(companyId)) {
            return userMapper.mapUserEntityToEmployeeProfileResponseModel(optEmployee.get());
        }
        throw new NotFoundRequestException("No such user", "0000");
    }

    @Override
    public EmployeeProfileResponseModel changeEmployeeProfile(EmployeeProfileRequestModel model, String userId){
        var optEmployee = userRepository.findById(userId);
        if (optEmployee.isEmpty() || optEmployee.get().getUserType() != UserType.EMPLOYEE){
            throw new NotFoundRequestException("No such user", "0000");
        }
        var userEntity = userMapper.mapEmployeeProfileRequestModelToUserEntity(optEmployee.get(), model);
        return userMapper.mapUserEntityToEmployeeProfileResponseModel(userEntity);
    }

    @Override
    public void sendVerificationLinkToEmail(String userId) {
        var user = getUserById(userId);
        if (user.getIsEmailVerified()){
            throw new CustomGeneralException("Email already verified", "0000");
        }
        var link = verificationService.getVerificationLink(userId, VerificationType.EMAIL);
        emailSenderService.sendMail(user.getEmail(), "Verify your account", link);
    }

    @Override
    public void sendVerificationNumbersToPhone(String userId) {
        var user = getUserById(userId);
        String phone;
        boolean verified;
        if (user.getUserType() == UserType.COMPANY){
            var company = companyService.getCompanyById(userId);
            var address = findCompanyMainAddress(company);
            verified = address.getContact().getIsVerified();
            phone = address.getContact().getPhone();
        } else if (user.getUserType() == UserType.EMPLOYEE) {
            phone = user.getEmployeeInfo().getPhone();
            verified = user.getEmployeeInfo().getIsPhoneVerified();
        } else {
            var address = findUserMainAddress(user);
            verified = address.getContact().getIsVerified();
            phone = address.getContact().getPhone();
        }
        if (verified){
            throw new CustomGeneralException("Your main phone number already verified", "0000");
        }
        var numbers = verificationService.getVerificationNumbers(userId, VerificationType.PHONE);
        phoneSenderService.sendSms(phone, numbers);
    }

    private Address findUserMainAddress(UserEntity user){
        var address = user.getAddresses().stream().filter(Address::getIsMain).findFirst();
        if(address.isEmpty()){
            throw new NotFoundRequestException("No main address or contact", "0000");
        }
        return address.get();
    }

    private Address findCompanyMainAddress(CompanyEntity company){
        var address = company.getAddresses().stream().filter(Address::getIsMain).findFirst();
        if(address.isEmpty()){
            throw new NotFoundRequestException("No main address or contact", "0000");
        }
        return address.get();
    }

    @Override
    public void verifyEmailByToken(String token) {
        var userId = verificationService.verifyToken(token);
        var user = getUserById(userId);
        user.setIsEmailVerified(true);
        userRepository.save(user);
        if(user.getUserType() == UserType.COMPANY){
            var company = companyService.getCompanyById(user.getCompanyUserInfo().getCompanyId());
            company.setIsEmailVerified(true);
            companyService.saveCompany(company);
        }
    }

    @Override
    public void verifyPhoneByNumbers(String numbers) {
        var userId = verificationService.verifyToken(numbers);
        var user = getUserById(userId);
        if(user.getUserType() == UserType.COMPANY){
            var company = companyService.getCompanyById(user.getCompanyUserInfo().getCompanyId());
            var companyAddress = findCompanyMainAddress(company);
            companyAddress.getContact().setIsVerified(true);
            companyService.saveCompany(company);
        } else if (user.getUserType() == UserType.EMPLOYEE) {
            user.getEmployeeInfo().setIsPhoneVerified(true);
        } else {
            var address = findUserMainAddress(user);
            address.getContact().setIsVerified(true);
        }
        userRepository.save(user);
    }
}
