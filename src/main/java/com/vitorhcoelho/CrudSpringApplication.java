package com.vitorhcoelho;

import com.vitorhcoelho.enums.Category;
import com.vitorhcoelho.model.Lesson;
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
			Course c = new Course();
			c.setName("Angular");
			c.setCategory(Category.FRONTEND);

			Lesson l = new Lesson();
			l.setName("Reactive Forms");
			l.setYoutubeUrl("2d2v4g0a1xM");
			l.setCourse(c);
			c.getLessons().add(l);

			Lesson l2 = new Lesson();
			l2.setName("Components");
			l2.setYoutubeUrl("3d2v4g0a1xM");
			l2.setCourse(c);
			c.getLessons().add(l2);

			courseRepository.save(c);

			Course c2 = new Course();
			c2.setName("Java + Spring");
			c2.setCategory(Category.BACKEND);

			Lesson l3 = new Lesson();
			l3.setName("Spring Boot");
			l3.setYoutubeUrl("6q2v4g0a1xM");
			l3.setCourse(c2);
			c2.getLessons().add(l3);

			Lesson l4 = new Lesson();
			l4.setName("Spring Data JPA");
			l4.setYoutubeUrl("7q111g0a1xM");
			l4.setCourse(c2);
			c2.getLessons().add(l4);

			courseRepository.save(c2);
		};

	}

}
