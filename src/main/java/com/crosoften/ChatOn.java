package com.crosoften;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ChatOn {
	
	public static void main(String[] args) {
		SpringApplication.run( ChatOn.class, args );
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
		return args -> {
			
			System.out.println( "Beans utilizados no projeto:" );
			
			String[] beansNames = applicationContext.getBeanDefinitionNames();
			Arrays.sort( beansNames );
			
			for (String beanName : beansNames) {
				System.out.println( beanName );
			}
		};
	}
}
