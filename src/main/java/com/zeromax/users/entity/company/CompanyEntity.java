package com.zeromax.users.entity.company;

import com.zeromax.users.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "companies")
public class CompanyEntity {

    @Id
    private String id;
    private String companyName;
    @Indexed(unique = true)
    private Integer companyTin;
    @Email
    @Indexed(unique = true)
    private String email;
    private String mainAddressId;
    private List<Address> addresses;
    private List<PaymentOptions> paymentOptions;
    private String pictureUrl;
    private List<String> categoryIds;
    private Double rating;
    private Boolean isEmailVerified = false;
    private Boolean isActive = true;
    private Boolean isFinished = false;

}
