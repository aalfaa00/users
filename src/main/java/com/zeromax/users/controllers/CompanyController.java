package com.zeromax.users.controllers;

import com.zeromax.users.models.*;
import com.zeromax.users.service.CompanyService;
import com.zeromax.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @PostMapping("employees/register")
    public ResponseEntity<Void> registerEmployee(@Valid @RequestBody EmployeeRegistrationRequestModel model,
                                                                         @RequestHeader("userId") String companyId){
        userService.createEmployee(model, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeProfileResponseModel>> getPaginatedEmployeesWithSorting(@RequestHeader("userId") String companyId,
                                                                                               @RequestParam(required = false) String keyword,
                                                                                               @RequestParam(required = false) String sortBy,
                                                                                               Pageable page){
        return ResponseEntity.ok().body(userService.getPaginatedEmployeesWithSorting(page, companyId, keyword, sortBy));
    }

    @GetMapping("employees/{employeeId}")
    public ResponseEntity<EmployeeProfileResponseModel> getEmployeeProfileByCompany(@RequestHeader("userId") String companyId,
                                                                                    @PathVariable("employeeId") String employeeId){
        var employee = userService.getEmployeeProfile(companyId, employeeId);
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/profile1")
    public ResponseEntity<BusinessUserProfileResponseModel> fillCompanyProfileFirstStep(@Valid @RequestBody
                                                                                            BusinessUserProfileFirstRequestModel model,
                                                                                        @RequestHeader("userId") String companyId){
        var company = companyService.fillBusinessUserProfileFirstStep(model, companyId);
        return ResponseEntity.ok().body(company);
    }

    @PostMapping("/profile2")
    public ResponseEntity<BusinessUserProfileResponseModel> fillCompanyProfileSecondStep(@Valid @RequestBody
                                                                                             BusinessUserProfileSecondRequestModel model,
                                                                                        @RequestHeader("userId") String companyId){
        var company = companyService.fillBusinessUserProfileSecondStep(model, companyId);
        return ResponseEntity.ok().body(company);
    }

    @PostMapping("/profile3")
    public ResponseEntity<BusinessUserProfileResponseModel> fillCompanyProfileThirdStep(@Valid @RequestBody
                                                                                         BusinessUserProfileThirdRequestModel model,
                                                                                         @RequestHeader("userId") String companyId){
        var company = companyService.fillBusinessUserProfileThirdStep(model, companyId);
        return ResponseEntity.ok().body(company);
    }
}
