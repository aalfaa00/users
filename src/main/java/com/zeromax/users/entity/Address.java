package com.zeromax.users.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    private String id;
    private String name;
    private String state;
    private String city;
    private Integer zipCode;
    private String address;
    private LocationModel location;
    private Contact contact;
    private Boolean isMain = false;
}
