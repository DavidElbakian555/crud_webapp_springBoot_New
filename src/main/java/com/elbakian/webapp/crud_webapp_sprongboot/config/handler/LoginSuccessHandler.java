package com.elbakian.webapp.crud_webapp_sprongboot.config.handler;


import com.elbakian.webapp.crud_webapp_sprongboot.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/users");
        } else {
            response.sendRedirect("/users/" + user.getId());
        }
    }
}