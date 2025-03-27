package com.rishi.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
public class JournalApplication {
	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
//		ConfigurableEnvironment environment = context.getEnvironment();
//		System.out.println(environment.getActiveProfiles()[0]);
	}

	@Bean
	public PlatformTransactionManager falana(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}

// PlatformTransactionManager --> interface
// MongoTransactionManager