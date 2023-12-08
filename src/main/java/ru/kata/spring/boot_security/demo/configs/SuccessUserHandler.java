package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        int redirect = 0;
        if (roles.contains("ROLE_USER")) {
            redirect = 1;
        }
        if(roles.contains("ROLE_ADMIN")) {
            redirect = 2;
        }
        switch (redirect) {
            case 0:
                httpServletResponse.sendRedirect("/");
                break;
            case 1:
                httpServletResponse.sendRedirect("/user");
                break;
            case 2:
                httpServletResponse.sendRedirect("/admin");
                break;
        }


    }
}