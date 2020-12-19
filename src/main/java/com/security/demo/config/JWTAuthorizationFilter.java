package com.security.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
	private JwtConfig jwtConfig;
	
	public JWTAuthorizationFilter(JwtConfig jwtConfig) {
		super();
		this.jwtConfig = jwtConfig;
	}





	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Authorizing jwt");
		String authHeader = request.getHeader(jwtConfig.getHeader());
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			return;
		}
		
		try {
		String token = authHeader.replace("Bearer", "");
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(jwtConfig.getSecret().getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody();
			
		String username = claims.getSubject();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch (SignatureException e) {
			response.getWriter().write("Invalid Token");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
