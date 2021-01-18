package com.app.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.app.bookstore.controller", "com.app.bookstore.service"})
@EntityScan("com.app.bookstore.model")
@EnableJpaRepositories("com.app.bookstore.repository")
public class BookstoreV2Application {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreV2Application.class, args);
	}

}
