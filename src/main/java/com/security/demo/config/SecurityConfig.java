package com.security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.security.demo.entity.User;
import com.security.demo.services.AppUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppUserService userService;
	
	@Autowired
	private JwtConfig jwtConfig;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtConfig))
			.addFilterAfter(new JWTAuthorizationFilter(jwtConfig), JWTAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/logout").permitAll()
			.antMatchers("/api/users/signup").permitAll()
			.anyRequest()
			.authenticated()
			;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	private AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		daoAuthenticationProvider.setUserDetailsService(username -> {
			User user = userService.loadByEmailId(username);
			return new UserDetailsImpl(user);
		});
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(10);
	}
	
}
