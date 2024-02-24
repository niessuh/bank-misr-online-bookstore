package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.entity.User;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenAuthenticationService {
    public void addAuthentication(HttpServletResponse res, String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, AppConstants.SECRET).compact();

        res.addHeader(AppConstants.ACCESS_CONTROL_EXPOSE, AppConstants.HEADER_AUTHORIZATION);
        res.addHeader(AppConstants.HEADER_AUTHORIZATION, AppConstants.TOKEN_PREFIX + " " + jwt);
    }

    public Authentication getAuthentication(HttpServletRequest request, UserService userService) {

        String user= null;
        try {
            String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (token != null && !token.isEmpty()) {
                // parse the token.

                String subject = Jwts.parser().setSigningKey(AppConstants.SECRET)
                        .parseClaimsJws(token.replace(AppConstants.TOKEN_PREFIX, "")).getBody().getSubject();
                int version = 1;
                if (subject.contains(AppConstants.DASH)) {
                    String[] subjects = subject.split(AppConstants.DASH);
                    user = subjects[0];
                    if (subjects[1] != null)
                        version = Integer.parseInt(subjects[1]);
                } else {
                    user = subject;
                }

                User userOb = getUser(userService, user, grantedAuthorities);
                if (userOb == null) {
                    return null;
                }

                return user != null ? new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities) : null;
            }
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        }
        return null;
    }

    private  User getUser(UserService userService, String user, List<GrantedAuthority> grantedAuthorities) {
        try {
            User userOb = userService.findByUsername(user);
            if (!userOb.isActive()) {
                return null;
            }

            List<Role> roles= userOb.getRoles();
            for (Role role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
            }
            return userOb;
        } catch (NotFoundException e) {
            return null;
        }
    }
}
