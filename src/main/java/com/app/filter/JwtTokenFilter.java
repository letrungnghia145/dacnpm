package com.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.helper.JwtUtils;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String JWT_TOKEN_FORMAT_TYPE = "Bearer ";
	@Autowired
	private UserDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		SecurityContext context = SecurityContextHolder.getContext();

		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		String token = authorizationHeader != null && authorizationHeader.startsWith(JWT_TOKEN_FORMAT_TYPE)
				? StringUtils.delete(authorizationHeader, JWT_TOKEN_FORMAT_TYPE)
				: null;
		if (token != null && context.getAuthentication() == null) {
			UserDetails userdetails = service.loadUserByUsername(JwtUtils.getUsernameFromToken(token));
			if (JwtUtils.isValidToken(token, userdetails)) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userdetails, null,
						userdetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource());
				context.setAuthentication(auth);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
