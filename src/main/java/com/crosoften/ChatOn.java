package com.crosoften;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
	ChatOn.class,
	Jsr310JpaConverters.class
})
public class ChatOn {
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault( TimeZone.getTimeZone( "America/Sao_Paulo" ) );
	}
	
	
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
