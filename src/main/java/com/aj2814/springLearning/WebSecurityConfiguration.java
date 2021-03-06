package com.aj2814.springLearning;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .authorities("ROLE_USER").build(),
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .authorities("ROLE_ACTUATOR", "ROLE_ADMIN", "ROLE_USER").build()
        );
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.to("health")).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/actuator/metrics/github.ratelimit.remaining").permitAll()
                .antMatchers("/h2-console/**").hasRole("ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and().httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
