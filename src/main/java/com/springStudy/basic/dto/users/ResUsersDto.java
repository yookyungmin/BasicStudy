package com.springStudy.basic.dto.users;

import com.springStudy.basic.entity.user.domain.Users;
import com.springStudy.basic.util.CustomDateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class ResUsersDto {
    @Getter
    @Setter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(Users user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }
    @ToString
    @Getter
    @Setter
    public static class JoinRespDto{
        private Long id;
        private String username;
        private String fullname;

        public JoinRespDto(Users user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }
}
