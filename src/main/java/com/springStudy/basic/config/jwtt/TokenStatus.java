package com.springStudy.basic.config.jwtt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenStatus {

    AUTHENTICATED,
    EXPIRED,
    INVALID
}
