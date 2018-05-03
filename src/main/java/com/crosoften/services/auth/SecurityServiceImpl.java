package com.crosoften.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public class SecurityServiceImpl {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// @TODO: Parei Aqui - https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/
	public SecurityServiceImpl() {
	
	}
	
}
