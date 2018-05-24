package com.crosoften;

import com.crosoften.configuration.FileStorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ChatOn extends SpringBootServletInitializer {

    @PostConstruct
    public void init() {
        //@TODO: Arrumar esse timezone.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatOn.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {

            System.out.println("Beans utilizados no projeto:");

            String[] beansNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beansNames);

            for (String beanName : beansNames) {
                System.out.println(beanName);
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ChatOn.class);
    }
}
