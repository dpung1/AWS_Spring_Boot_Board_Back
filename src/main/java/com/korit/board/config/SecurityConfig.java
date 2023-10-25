package com.korit.board.config;

import com.korit.board.Filter.JwtAuthenticationFilter;
import com.korit.board.security.PrincipalEntryPoint;
import com.korit.board.security.oauth2.OAuth2SuccessHandler;
import com.korit.board.service.PrincipalUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalEntryPoint principalEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PrincipalUserDetailService principalUserDetailService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/auth/**", "/board/**", "/boards/**")
                .permitAll() // 위에 있는 경로 접속시 길을 막지 않음
                .antMatchers("/board/content")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(principalEntryPoint)
                .and()
                // oauth 설정
                .oauth2Login()
                .loginPage("http://localhost:3000/auth/signin")
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint()
                .userService(principalUserDetailService);

    }
}
