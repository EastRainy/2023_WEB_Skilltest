package com.practice.Skilltest.security.handler;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthFailHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("Auth Fail");

        //실패시 로그에 인증 실패 메세지

        String redirectUrl = "/login";
        String errMsg = AuthFailExceptionMessage.getMessage(exception);


        request.setAttribute("message",errMsg);

        request.getRequestDispatcher(redirectUrl).forward(request,response);
        //에러에 따른 메세지 추출하여 에러메세지 response

    }
}

