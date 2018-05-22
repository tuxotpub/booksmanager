package org.tuxotpub.booksmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BooksmanagerV1Application {

	public static void main(String[] args) {
		SpringApplication.run(BooksmanagerV1Application.class, args);
	}
}
