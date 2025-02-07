package com.springStudy.basic.entity.user.service;

import com.springStudy.basic.dto.users.ResUsersDto;
import com.springStudy.basic.entity.user.domain.UserRepository;
import com.springStudy.basic.entity.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    @Override
    public Users createUser(String name, String email) {
        Users users = new Users(name, email);

        return repository.save(users);
    }

    @Override
    public List<Users> getAllUser() {
        return repository.findAll();
    }

    @Override
    public ResUsersDto getUserByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Users getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}
