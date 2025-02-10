package com.springStudy.basic.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.logging.LogRecord;
import com.springStudy.basic.SessionConst;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //http 프로토콜 기반의 기능을 지원하는 메서드를 사용하기 위해 형변환
        //ServletRequest는 네트워크기반의 메서드 제공
        // HttpServletRequest 인터페이스는 ServletRequest를 상속받습니다.

        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpresponse = (HttpServletResponse) response;


        try{
            log.info("인증 체크 필터 시작", requestURI);

            if(isLoginCheckPath(requestURI)){
                log.info("인증체크 로직 실행", requestURI);
                HttpSession session = httpRequest.getSession(false);

                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청{}", requestURI);
                    //로그인으로 redirect

                    httpresponse.sendRedirect("/login?redirectURL=" + requestURI);
                    //상품 등록 누르고 로그인 화면으로 이동 뒤 로그인ㄴ 후 다시 상품목록 화면으로 이도아기 위해 uri넘겨주기
                    return;
                }
            }
            chain.doFilter(request, response);
        }catch (Exception e){
            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 봬주어야 함

        }finally {
            log.info("인증 체크 필터종료{} ", requestURI);
        }
    }

    /*
     화이트 리스트 경우인증 체크
     */
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
        //화이트리스트에 없는 건 false
    }

}
