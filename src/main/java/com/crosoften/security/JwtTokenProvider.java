package com.crosoften.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( JwtTokenProvider.class );
	
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		Date dateNow = new Date();
		Date expirationDate = new Date( dateNow.getTime() + jwtExpirationInMs );
		
		return Jwts.builder().setSubject( Long.toString( userPrincipal.getId() ) ).setIssuedAt( new Date() )
			       .setExpiration( expirationDate ).signWith( SignatureAlgorithm.HS512, jwtSecret ).compact();
	}
	
	public Long getUserIdFromJWT(String tonken) {
		Claims claims = Jwts.parser().setSigningKey( jwtSecret ).parseClaimsJws( tonken ).getBody();
		
		return Long.parseLong( claims.getSubject() );
	}
	
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey( jwtSecret ).parseClaimsJws( authToken );
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			LOGGER.error( e.getMessage() );
		}
		return false;
	}
	
	
}
