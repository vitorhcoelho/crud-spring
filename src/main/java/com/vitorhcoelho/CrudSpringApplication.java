package com.vitorhcoelho;

import com.vitorhcoelho.enums.Category;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.repository.CourseRepository;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
//			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular");
			c.setCategory(Category.FRONTEND);

			courseRepository.save(c);

			Course c2 = new Course();
			c2.setName("Java + Spring");
			c2.setCategory(Category.BACKEND);

			courseRepository.save(c2);
		};

	}

}
