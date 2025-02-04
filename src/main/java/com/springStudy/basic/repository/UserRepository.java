package com.springStudy.basic.repository;

import com.springStudy.basic.entity.Users;
import com.springStudy.basic.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
