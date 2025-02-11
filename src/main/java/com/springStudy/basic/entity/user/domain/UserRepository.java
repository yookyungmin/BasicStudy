package com.springStudy.basic.entity.user.domain;

import com.springStudy.basic.dto.users.ResUsersDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // 메서드 이름으로 쿼리 생성
    ResUsersDto findByName(String name);

    Optional<Users> findByUserName(String name);

    @Query("SELECT u From Users u WHERE u.email =: email" )
    Users findUserByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM Users WHERE name = :name", nativeQuery = true)
    List<Users> findUsersByNameNative(@Param("name") String name);
}
