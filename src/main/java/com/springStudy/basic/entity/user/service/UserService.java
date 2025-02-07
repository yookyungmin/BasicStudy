package com.springStudy.basic.entity.user.service;

import com.springStudy.basic.dto.users.ResUsersDto;
import com.springStudy.basic.entity.user.domain.Users;

import java.util.List;

public interface UserService {

    Users createUser(String name, String email);
    List<Users> getAllUser();
    ResUsersDto getUserByName(String name);

    Users getUserByEmail(String email);
}
