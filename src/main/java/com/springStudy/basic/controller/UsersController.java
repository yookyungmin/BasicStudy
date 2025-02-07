package com.springStudy.basic.controller;

import com.springStudy.basic.dto.ResponseDto;
import com.springStudy.basic.dto.users.ReqUserDto;
import com.springStudy.basic.dto.users.ResUsersDto;
import com.springStudy.basic.entity.user.domain.Users;
import com.springStudy.basic.entity.user.service.UserService;
import com.springStudy.basic.entity.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping()
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserByName(@RequestBody ReqUserDto reqUserDto, BindingResult bindingResult){

        ResUsersDto userByName = userService.getUserByName(reqUserDto.getName());
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 가져오기 성공", userByName), HttpStatus.OK);
    }
}
