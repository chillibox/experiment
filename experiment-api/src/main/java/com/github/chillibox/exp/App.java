package com.github.chillibox.exp;

import com.github.chillibox.exp.service.AppUserDetailsService;
import com.github.chillibox.exp.spring.AjaxAuthFailHandler;
import com.github.chillibox.exp.spring.AjaxAuthSuccessHandler;
import com.github.chillibox.exp.spring.AjaxAwareLoginEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static com.github.chillibox.exp.utils.Constants.ROLE_ADMIN;
import static com.github.chillibox.exp.utils.Constants.ROLE_STAFF;

/**
 * 应用程序入口
 */

@SpringBootApplication(scanBasePackages = "com.github.chillibox.exp")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AjaxAwareLoginEntryPoint entryPoint() {
        return new AjaxAwareLoginEntryPoint("/admin/login");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @EnableWebSecurity
    public static class AdminConsoleSecurityConfig extends WebSecurityConfigurerAdapter {

        @Value("${app.rememberme.days:7}")
        private int remembermeDays;

        @Value("${app.rememberme.key}")
        private String remembermeKey;

        @Autowired
        private AppUserDetailsService appUserDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AjaxAwareLoginEntryPoint ajaxAwareLoginEntryPoint;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(appUserDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
                    .exceptionHandling().authenticationEntryPoint(ajaxAwareLoginEntryPoint).and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .successHandler(new AjaxAuthSuccessHandler())
                    .failureHandler(new AjaxAuthFailHandler())
                    .permitAll()
                    .and()
                    .logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout").permitAll()
                    .and()
                    .authorizeRequests().antMatchers(
                    "/admin/user/**", "/admin/user",
                    "/admin/api/user/**", "/admin/api/user").hasRole(ROLE_ADMIN)
                    .and()
                    .authorizeRequests().antMatchers("/admin/**", "/admin").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/login", "/css/**", "/js/**", "/fonts/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .cors()
                    .and()
                    .rememberMe().key(remembermeKey).tokenValiditySeconds(60 * 60 * 24 * remembermeDays);
        }
    }
}
