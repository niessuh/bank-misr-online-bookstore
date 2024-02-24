package com.bankmisr.onlinebookstore.filter;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.entity.User;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.service.TokenAuthenticationService;
import com.bankmisr.onlinebookstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{
   private UserService userService;
   private TokenAuthenticationService tokenAuthenticationService;
    public JWTLoginFilter(String url, AuthenticationManager authManager, UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Authentication auth = null;
        try {
            auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
                    creds.getPassword(), grantedAuthorities));
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(AppConstants.CONTENT_TYPE);
            response.getWriter().println("{\"message\": \"" + e.getMessage() + "\"}");
            logger.error("there is no user with userName: " + e.getMessage());
        }

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        User user;
        try {
            user = userService.findByUsername(auth.getName());
            tokenAuthenticationService.addAuthentication(res, auth.getName());
            res.setContentType(AppConstants.CONTENT_TYPE);
            res.getWriter().write(new ObjectMapper().writeValueAsString(user));
        } catch (NotFoundException e) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.setContentType(AppConstants.CONTENT_TYPE);
            res.getWriter().println("{\"message\": \"This user does not exist \"}");
            logger.error("there is no user with userName: " + auth.getName() + e.getMessage());

        }

    }
}
