package com.zeromax.users.controllers;

import com.zeromax.users.exeptions.InvalidRequestException;
import com.zeromax.users.models.*;
import com.zeromax.users.service.UserService;
import com.zeromax.users.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequestModel model,
                                              @RequestHeader("userId") String userId){
        userService.updatePassword(model, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/password/request")
    public ResponseEntity<Void> passwordResetRequest(@Valid @RequestBody PasswordResetRequestModel passwordResetRequestModel){
        var email = passwordResetRequestModel.getEmail();
        userService.sendLinkToResetPassword(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseModel> registerUser(@Valid @RequestBody CreateUserModel user)
            throws InvalidRequestException {
        CreateUserResponseModel userResponseModel = userService.createUser(user);
        return new ResponseEntity(userResponseModel, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public void logoutUser(@RequestHeader("userId") String userId){
        userService.logout(userId);
    }

    @PostMapping ("/delete")
    public void deleteUser(@RequestHeader("userId") String userId){
        userService.deleteUser(userId);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<LoginResponseModel> refreshToken(@RequestHeader("refreshToken") String refreshToken){
        LoginResponseModel responseModel = userService.refreshToken(refreshToken);
        return ResponseEntity.ok().body(responseModel);
    }

    @PostMapping("/customer/profile")
    public ResponseEntity<SimpleUserProfileResponseModel> fillSimpleUserProfileInfo(@Valid @RequestBody
                                                                                        SimpleUserProfileInfoModel model,
                                                                                    @RequestHeader("userId")
                                                                                    String userId){
        var user = userService.fillSimpleUserProfileInfo(model, userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("customer/profile")
    public ResponseEntity<SimpleUserProfileResponseModel> getSimpleUSerProfile(@RequestHeader("userId") String userId){
        var customer = userService.getSimpleUserProfile(userId);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/commercial/profile1")
    public ResponseEntity<CommercialUserProfileResponseModel> fillCommercialUserFirstProfileInfo(@Valid @RequestBody
                                                                                    CommercialUserProfileFirstRequestModel model,
                                                                                    @RequestHeader("userId")
                                                                                    String userId, @RequestHeader("Authorization") String jwt){
        var user = userService.fillCommercialUserProfileFirstStep(model, userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/commercial/profile2")
    public ResponseEntity<CommercialUserProfileResponseModel> fillCommercialUserSecondProfileInfo(@Valid @RequestBody
                                                                                                 CommercialUserProfilesSecondRequestModel model,
                                                                                                 @RequestHeader("userId")
                                                                                                 String userId){
        var user = userService.fillCommercialUserProfileSecondStep(model, userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/employee")
    public ResponseEntity<EmployeeProfileResponseModel> getEmployeeProfile(@RequestHeader("userId") String userId){
        var employee = userService.getEmployeeProfile(null, userId);
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employee")
    public ResponseEntity<EmployeeProfileResponseModel> changeEmployeeProfile(@Valid @RequestBody
                                                                                  EmployeeProfileRequestModel model,
                                                                              @RequestHeader("userId") String userId){
        var employee = userService.changeEmployeeProfile(model, userId);
        return ResponseEntity.ok().body(employee);
    }
}
