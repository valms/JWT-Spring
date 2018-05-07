package com.crosoften.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger( JwtAuthenticationFilter.class );
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		
		try {
			String jwt = getJwtFromRequest( httpServletRequest );
			
			if (StringUtils.hasText( jwt ) && this.jwtTokenProvider.validateToken( jwt )) {
				Long userId = this.jwtTokenProvider.getUserIdFromJWT( jwt );
				
				UserDetails userDetails = customUserDetailsService.loadUserById( userId );
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
				
				authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( httpServletRequest ) );
				
				SecurityContextHolder.getContext().setAuthentication( authenticationToken );
			}
		} catch (Exception e) {
			LOGGER.error( "Could not set user authentication in security context", e );
		}
		
		filterChain.doFilter( httpServletRequest, httpServletResponse );
		
	}
	
	
	private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
		String bearearToken = httpServletRequest.getHeader( "Authorization" );
		if (StringUtils.hasText( bearearToken ) && bearearToken.startsWith( "Bearer" )) {
			return bearearToken.substring( 7, bearearToken.length() );
		}
		return null;
	}
}
