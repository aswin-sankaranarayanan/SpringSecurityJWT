package com.security.demo.config;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	private AuthenticationManager authenticationManger;
	private JwtConfig jwtConfig;
	

	public JWTAuthenticationFilter(AuthenticationManager authenticationManger, JwtConfig jwtConfig) {
		super();
		this.authenticationManger = authenticationManger;
		this.jwtConfig = jwtConfig;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("Authentication Starts");
		Authentication authentication = null;
		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			authentication =  authenticationManger
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return authentication;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		logger.info("successfulAuthentication Starts");
		String token = Jwts
			.builder()
			.setSubject(authResult.getName())
			.setExpiration(new Date(System.currentTimeMillis()+jwtConfig.getExpiration()))
			.signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
			.compact();
			
		response.getOutputStream().write(token.getBytes());
	}
	
}
