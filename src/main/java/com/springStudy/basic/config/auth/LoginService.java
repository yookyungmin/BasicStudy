package com.springStudy.basic.config.auth;

import com.springStudy.basic.entity.user.domain.UserRepository;
import com.springStudy.basic.entity.user.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login")
//login 요청이 오면 자동으로 UserDetailService타입으로 Ioc되어 있는 loadUserByUsername 함수 실행
@Service //UserDetailsService . 유저의 정보를 가져오는 인터페이스이다
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티로 로그인이 될때 시큐리티가 loadUserByUsername()을 실행해서 username체크
    //체크해서 없으면 오류, 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인 된 세션이 만들어진다.
    @Override //String username 매개변수는 인풋태그 name이랑 동일한 이름을 가져야 함
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //로그인 될떄 세션 만들어주는과정
        Users userPS = userRepository.findByUserName(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증실패")
                //시큐리티를 타고 있을때 username을 못찾으면 new Internal~ 얘로 제어를해줘야 한다. 시큐리티를 타고 있을떈 제어권이 없기 떄문
                //컨트롤러까지 도달이 안된 상태라 customexceptionhandler로 관리를 못한다
                //unsuccessfulAuthentication() 얘가 처리
        );

        return new LoginUser(userPS); //username을 찾으면 userPS를 담아서 세션에 만들어진다
    }
}
