package com.zeromax.users.entity.company;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CompanyUserRole {

    SUPERUSER("SUPERUSER"),
    ADMIN("ADMIN");

    String slug;

    CompanyUserRole(String slug){
        this.slug = slug;
    }

    @JsonValue
    public String getSlug() {
        return slug;
    }
}
