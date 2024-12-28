package com.InventoryManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   .antMatchers("/logout","/static/**", "/login", "/signup").permitAll()
                   .anyRequest().authenticated()
                   .and()
                .formLogin()
                   .loginPage("/login")
                   .failureUrl("/login?error=true")
                   .and()
                .formLogin()
                   .successHandler(successHandler())
                   .and()
                .logout()
                   .logoutUrl("/logout")  // Specify the URL for logout
                   .logoutSuccessUrl("/login")  // Specify the URL to redirect after logout
                   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID")
                   .and()
                .exceptionHandling()
                   .accessDeniedPage("/access-denied")
                   .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                String targetUrl = super.determineTargetUrl(request, response, authentication);
                if (targetUrl.equals("/")) {

                    //Typed url stored in savedRequest
                    SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

                    if (savedRequest != null) {
                        targetUrl = savedRequest.getRedirectUrl();
                    } else {
                        // No saved request URL, redirect to a default home page
                        targetUrl = "/home";
                    }
                }
                return targetUrl;
            }
        };
    }

}
