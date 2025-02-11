package com.springStudy.basic.entity.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEnum {
    ADMIN("관리자"),
    CUSTOMER("고객");

    private String value;
}
