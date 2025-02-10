package com.springStudy.basic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.StyledEditorKit;
import java.util.UUID;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //@RequestMapping: handleMethod
        //정적리소스 : ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler; //호출할 컨트롤러 모든 정보가 포함 되어있다
        }
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        return true;
    } //컨트롤러 호출전

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //에외가 발생하면 posthandle은 호출이 안되는데 애프터는 호출이됨
        String requestURI = request.getRequestURI();
    }
}

