package com.zeromax.users.controllers;

import com.zeromax.users.utils.Constants;
import com.zeromax.users.models.*;
import com.zeromax.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class UtilsController {

    private final UserService userService;

    public UtilsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/verify/email")
    public ResponseEntity<Void> sendVerificationTokenToEmail(@RequestHeader("userId") String userId){
        userService.sendVerificationLinkToEmail(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify/phone")
    public ResponseEntity<Void> sendVerificationNumbersToPhone(@RequestHeader("userId") String userId){
        userService.sendVerificationNumbersToPhone(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify/email/{token}")
    public ResponseEntity<Void> verifyEmail(@PathVariable("token") String token){
        userService.verifyEmailByToken(token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify/phone/{numbers}")
    public ResponseEntity<Void> verifyPhone(@PathVariable("numbers") String numbers){
        userService.verifyPhoneByNumbers(numbers);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/password/reset/{token}")
    public RedirectView passwordReset(@PathVariable String token, @RequestBody ResetPasswordModel resetPasswordModel){
        userService.resetPassword(token, resetPasswordModel.getPassword());
        var redirectLink = String.format("%s/users/auth/password/success", Constants.DIRECT_LINK);
        return new RedirectView(redirectLink);
    }

    @PostMapping("/employee/register/{token}")
    public ResponseEntity<EmployeeRegistrationResponseModel> employeeCompleteRegistration(@PathVariable("token") String token,
                                                                                          @Valid @RequestBody
                                                                           EmployeeCompleteRegistrationRequestModel model){
        var employee = userService.employeeCompleteRegistration(model, token);
        return ResponseEntity.ok().body(employee);
    }
}
