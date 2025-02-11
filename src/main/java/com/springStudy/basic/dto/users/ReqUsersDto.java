package com.springStudy.basic.dto.users;

import com.springStudy.basic.entity.user.domain.UserEnum;
import com.springStudy.basic.entity.user.domain.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

public class ReqUsersDto {

    @Getter
    @Setter
    public static class LoginReqDto{
        private String username;
        private String password;
    }
    @Getter
    @Setter
    public static class JoinReqDto{
        //유효성 검사

        //영문, 숫자는 되고 길이 최소  20자 이내

        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
        @NotEmpty //null이거나 공백일 수 없다.
        private String username;

       //길이 4~20
        @NotEmpty
        @Size(min=4, max=20)
        private String password;

        //이메일 형식
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성 해주세요")
        @NotEmpty
        private String email;

        //영어, 한글, 2~20
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~2에서 20자 이내로 작성해주세요")
        @NotEmpty
        private String fullname;

        public Users toEntity(BCryptPasswordEncoder passwordEncoder){
            return Users.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}
