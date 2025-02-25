package com.springStudy.basic.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springStudy.basic.config.auth.LoginUser;
import com.springStudy.basic.entity.user.domain.UserEnum;
import com.springStudy.basic.entity.user.domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

public class JwtProcess {
    private final Logger log = LoggerFactory.getLogger(getClass());

    //토큰 생성
    public static String create(LoginUser loginUser){//토큰 생성에 책임
        String jwtToken = JWT.create()
                .withSubject("bank")//토큰의 제목
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtVo.EXPIRATION_TIME))//현재시간+만료시간 //토큰 유효기간
                .withClaim("id", loginUser.getUser().getId())  //
                .withClaim("role", loginUser.getUser().getRole()+"")//enumtype이라 롤
                .sign(Algorithm.HMAC512(jwtVo.SECRET));//노출되면 안되는 Vo에 시크릿키

        return jwtVo.TOKEN_PREFIX+jwtToken;
    }
    //토큰 검증
    //토큰 검증(return 되는 LoginUser  객체를 강제로 시큐리티 세션에 직접 주입할 예정)
    public static LoginUser verify(String token){ //토큰을 문자열로 받아서
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtVo.SECRET)).build()
                .verify(token); //토큰을 넣으면 검증이된다

        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();

        Users user = Users.builder().id(id).role(UserEnum.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);

        return loginUser;
    }
}
