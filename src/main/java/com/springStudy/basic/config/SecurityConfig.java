package com.springStudy.basic.config;

import com.springStudy.basic.config.jwt.JwtAuthenticationFilter;
import com.springStudy.basic.config.jwt.JwtAuthorizationFilter;
import com.springStudy.basic.entity.user.domain.UserEnum;
import com.springStudy.basic.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : Bycrpt 빈등록");

        return new BCryptPasswordEncoder();
    }

    //추후 jwt 필터 등록
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity>{
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager)); //강제 세션 로그인을 위해 AuthenticationManager 필요//loginForm.disabled해놓고 사용
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }

        public HttpSecurity build(){
            return getBuilder();
        }
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        log.debug("디버그 : filterChain 빈등록");

        http.headers(headers -> headers.disable());
        http.csrf(AbstractHttpConfigurer::disable); //csrf disable
        http.csrf(csrf -> csrf.disable());  // enable이면 post 맨 적용안함(메타코딩 유튜브 시큐리티 강의) 인증된 사용자가 사이트에 특정요청을 보내 사이트간 위조 요청방지
        http.cors(cors -> cors.configurationSource(configurationSource())); //자바스크립트에서 요청하는걸 막겠따는건데 허용

        //jsessionId를 서버쪽에서 관리안하겠다는 뜻
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //react, 앱 같은데서 요청을 받을거라 form로그인 방식 x jwt를 쓰니가
        http.formLogin(form-> form.disable());

        //브라우저가 팝업창을 이용해서 사용자 인증을 진행한다. HTTP 기본인증 비활성화
        http.httpBasic(httpBasic -> httpBasic.disable());

        // 필터 적용
        http.with(new CustomSecurityFilterManager(), c-> c.build());
        //빌더 패턴을 이용하지 않기 떄문에 apply가 this를 리턴하지 않아서 with메서드는 this를 리턴하게됨.

        // 인증 실패 처리
        http.exceptionHandling(e-> e.authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        }));
        //여기서 터지면 Authorization 인가는 통과했다는 뜻
        //헤더에 jwt토큰이 없어도 dofilter를 통해서 컨트롤러까지 갔다가 해당 코드가 낚아챔
        //postman에서 에러 나오는걸 통제하기위해 AuthenticationEntryPoint의 제어권을 뺏는다


        // 권한 실패 처리
        http.exceptionHandling(e-> e.accessDeniedHandler((request, response, accessDeniedException) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다", HttpStatus.FORBIDDEN);
        }));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/s/**").authenticated()
                .requestMatchers("/api/admin/**").hasRole(UserEnum.ADMIN.name())
                .anyRequest().permitAll()
        );
         /*  .and() // jwt가 아닌 form 로그인 방식
                .formLogin()
                .loginPage("/loginForm"); //권한이 있어야 하는 페이지 들어갈떄 로그인 페이지 이동하게끔
                .loginProcessingUrl("/login")// login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
                .defaultSuccessUrl("/"); //로그인이 대신 완료되면 /로 이동

             //시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
             //로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다(Seucrity ContextHolder)
             //오브젝트 => Authentication 타입 객체
             //Authentication 안에 User 정보가 있어야 됨.
             //User오브젝트 타입=> UserDetails 타입 객체

             //Security Session 영역에  Authentication 객체를 저장할수 있는데,
              Authentication 에 저장할 유저정보는 UserDetails 타입
              UserDetails 타입을 꺼내면 User오브젝트에 접근 가능
               Security Session => Authhentication => UserDetails(LoginUser)

              Authhentication 객체를 만들어서 Session에 넣기 위해선 UserDetailsService 필요
               로그인을 하게 되면 LoginService(UserDetailsService) 거쳐서 LoginUser(UserDetails)를 반환 해준다
                그후 security session에 Authhentication을 넣어줌 =>로그인완료
             */

        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterchain 등록");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*"); //모든 헤더를 다 받겠다
        configuration.addAllowedMethod("*"); //모든 메서드 get,post, put, delete(자바스크립트 요청 허용
        configuration.addAllowedOriginPattern("*"); //모든 ip주소 허용(프론트 엔드 ip만 허용 react, 핸드폰은 자바스크립트로 요청하는게 아니라 cors에 안걸림 )
        configuration.setAllowCredentials(true); //클라이언트에서 쿠키 요청 허용, 내서버가 응답을 할떄 json을 자바스크립트에서 처리할수 있게 할지 설정
        configuration.addExposedHeader("Authorization"); //지우면 Authorization이 null로뜸 //브라우저에 있는 Authorization을 자바스크립트로 가져올수 있다/옛날엔 기본값
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 주소 요청에, configuration 설정을 넣어주겠다.

        return source;
    }
}
