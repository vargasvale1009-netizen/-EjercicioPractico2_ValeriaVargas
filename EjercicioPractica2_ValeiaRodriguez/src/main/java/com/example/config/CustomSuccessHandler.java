package com.example.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/usuarios");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEDICO"))) {
            response.sendRedirect("/citas");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE"))) {
            response.sendRedirect("/citas");
        } else {
            response.sendRedirect("/login");
        }
    }
}
