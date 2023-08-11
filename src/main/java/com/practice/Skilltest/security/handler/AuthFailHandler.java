package com.practice.Skilltest.security.handler;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
public class AuthFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        System.out.println("Auth Fail");

        //실패시 로그에 인증 실패 메세지

        String redirctUrl = "/login?message=";
        String errMsg = URLEncoder.encode(AuthFailExceptionMessage.getMessage(exception), "UTF-8");


        response.sendRedirect(redirctUrl + errMsg);

    }
}
