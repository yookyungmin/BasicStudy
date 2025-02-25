package com.springStudy.basic.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RequiredArgsConstructor
@Configuration
public class QueryDSLConfig {

    @PersistenceContext
    private final EntityManager em;

    @Bean
    public JPAQueryFactory queryFactory(){
        return new JPAQueryFactory(em);
    }

}
