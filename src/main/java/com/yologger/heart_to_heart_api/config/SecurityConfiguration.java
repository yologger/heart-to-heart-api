package com.yologger.heart_to_heart_api.config;

import com.yologger.heart_to_heart_api.common.filter.ValidateAccessTokenFilter;
import com.yologger.heart_to_heart_api.common.util.JwtUtil;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.auth.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MemberDetailsService memberDetailsService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    private static final List<String> NOT_FILTERED_URLS = Arrays.asList(
            "/profile",
            "/auth/emailVerificationCode",
            "/auth/confirmVerificationCode",
            "/auth/join",
            "/auth/login",
            "/auth/logout",
            "/auth/reissueToken",
            "/test/test1",
            "/test/test2",
            "/test/port"
    );

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 시 MemberDetailsService를 사용하도록 지정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(memberDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // AuthenticationManager를 빈으로 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // AccessToken 검증 필터 빈으로 등록
    @Bean
    public ValidateAccessTokenFilter validateAccessTokenFilter() {
        ValidateAccessTokenFilter filter = new ValidateAccessTokenFilter(jwtUtil, NOT_FILTERED_URLS, memberRepository);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(validateAccessTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/test/**").permitAll()
                        .antMatchers("/auth/**").permitAll()
                        .antMatchers("/post/**").permitAll()
                        .antMatchers("/member/**").permitAll()
                        .antMatchers("/profile").permitAll()
                        .anyRequest().authenticated()
                );
    }
}
