package com.bankmisr.onlinebookstore.filter;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.service.TokenAuthenticationService;
import com.bankmisr.onlinebookstore.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {
    private UserService userService;
    private TokenAuthenticationService tokenAuthenticationService;
    public JWTAuthenticationFilter(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            logger.info("API calling"+req.getServletPath());
            String origin = req.getHeader("Origin");
            res.setHeader("Access-Control-Allow-Origin", origin );
            res.setHeader("Vary", "Origin");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
            Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest, userService);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ExpiredJwtException e) {

            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(AppConstants.CONTENT_TYPE);
            res.getWriter().println("{\"message\": \"Token expired\"}");
            logger.error("Token expired " + e.getMessage());

        }
        catch(SignatureException | MalformedJwtException e){

            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(AppConstants.CONTENT_TYPE);
            res.getWriter().println("{\"message\": \"Token not valid\"}");
            logger.error("Token not valid " + e.getMessage());

        }
    }
}
