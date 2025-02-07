package com.springStudy.basic.dto.users;

import lombok.Data;

@Data
public class ReqUserDto {
    private Long id;
    private String name;
    private String email;
}
