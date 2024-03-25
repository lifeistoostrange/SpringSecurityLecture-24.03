package com.example.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(auth -> auth.disable())			// 괄호 내 람다함수
			.headers(x -> x.frameOptions(y -> y.disable()))		// CK Editor image upload
			.authorizeHttpRequests(auth -> auth
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers("/user/login", "/user/register", 
							"/img/**", "/css/**", "/js/**", "/error/**").permitAll()
					.requestMatchers("/admin/**").hasAuthority("ADMIN")
					.anyRequest().authenticated()
					)
			.formLogin(auth -> auth
					.loginPage("/user/login")			// login form
					.loginProcessingUrl("user/login") 	// UserDetailService 구현객체에서 처리해야함
					.usernameParameter("uid")
					.passwordParameter("pwd")
					.defaultSuccessUrl("/user/loginSuccess", true)
					.permitAll()
			)
		;
		
		return http.build();
	}
	
}
