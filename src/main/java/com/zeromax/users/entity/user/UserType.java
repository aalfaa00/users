package com.zeromax.users.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {

    @JsonProperty("EMPLOYEE")
    EMPLOYEE("EMPLOYEE"),
    @JsonProperty("COMMERCIAL")
    COMMERCIAL("COMMERCIAL"),
    @JsonProperty("SIMPLE")
    SIMPLE("SIMPLE"),
    @JsonProperty("COMPANY")
    COMPANY("COMPANY"),
    @JsonProperty("ROOT")
    ROOT("ROOT");

    final String slug;

    UserType(String slug){
        this.slug = slug;
    }

    @JsonValue
    public String getSlug() {
        return slug;
    }
}
