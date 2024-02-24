package com.bankmisr.onlinebookstore.config;

import com.bankmisr.onlinebookstore.filter.JWTAuthenticationFilter;
import com.bankmisr.onlinebookstore.filter.JWTLoginFilter;
import com.bankmisr.onlinebookstore.service.TokenAuthenticationService;
import com.bankmisr.onlinebookstore.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.bankmisr.onlinebookstore.encryption.Encryption;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;

    private final Encryption encryption;

    @Value("${swagger.api.url}")
    private String swaggerApiUrls;

    public SecurityConfig(UserService userService, TokenAuthenticationService tokenAuthenticationService, Encryption encryption) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.encryption = encryption;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/bookstore/v1/login").permitAll().antMatchers("/swagger-ui.html").permitAll().antMatchers("/swagger-resources/**").permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/v2/api-docs").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new JWTLoginFilter("/bookstore/v1/login", authenticationManager()
                                 , userService, tokenAuthenticationService),
                                 UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(userService, tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);

        http.cors().configurationSource(new CorsConfigurationSource() {

            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.addAllowedOrigin("*");
                config.setAllowCredentials(true);
                return config;
            }
        });
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(encryption);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }


}
