package com.zeromax.users.entity.user;

import com.zeromax.users.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

  @Id
  private String id;
  @Indexed(unique = true)
  @Email
  private String email;
  private String firstName;
  private String lastName;
  private Date dateOfBirth;
  private UserType userType;
  private String password;
  private EmployeeInfo employeeInfo;
  private CommercialInfo commercialInfo;
  private CompanyUserInfo companyUserInfo;
  private String pictureUrl;
  private List<Address> addresses;
  private Boolean isEmailVerified = false;
  private Boolean isActive = true;
  private Boolean isFinished = false;
}
