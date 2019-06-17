package com.github.chillibox.exp.spring;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Created on 2017/7/18.</p>
 *
 * @author Gonster
 */
public class AjaxAwareLoginEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final RequestMatcher ajaxRequestMatcher
            = new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");

    public AjaxAwareLoginEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (ajaxRequestMatcher.matches(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            super.commence(request, response, authException);
        }
    }
}
