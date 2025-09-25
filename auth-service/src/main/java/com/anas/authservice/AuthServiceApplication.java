package com.anas.authservice;

import com.anas.authservice.entites.Person;
import com.anas.authservice.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(PersonRepository repository) {
		return args -> {
			// Check if data already exists to avoid duplicates
			if (repository.count() == 0) {
				// Create some sample Person entities
				Person person1 = Person.builder()
						.name("John Doe")
						.email("john.doe@example.com")
						.build();

				Person person2 = Person.builder()
						.name("Jane Smith")
						.email("jane.smith@example.com")
						.build();

				Person person3 = Person.builder()
						.name("Bob Johnson")
						.email("bob.johnson@example.com")
						.build();

				// Save to database
				repository.save(person1);
				repository.save(person2);
				repository.save(person3);

				System.out.println("Sample data initialized in database");
			}
		};

	}

}
