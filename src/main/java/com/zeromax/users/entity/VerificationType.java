package com.zeromax.users.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VerificationType {

    PHONE("PHONE"),

    EMAIL("EMAIL");

    String slug;

    VerificationType(String slug){
        this.slug = slug;
    }

    @JsonValue
    public String getSlug() {
        return slug;
    }
}
