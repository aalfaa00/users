package com.zeromax.users.entity.company;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentOptions {

    CREDIT("CREDIT/DEBIT"),
    CASH("CASH"),
    ALL("ALL");

    final String slug;

    PaymentOptions(String slug){
        this.slug = slug;
    }

    @JsonValue
    public String getSlug() {
        return slug;
    }
}
