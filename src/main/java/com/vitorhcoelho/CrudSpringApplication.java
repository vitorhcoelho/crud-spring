package com.vitorhcoelho;

import com.vitorhcoelho.enums.Category;
import com.vitorhcoelho.model.Lesson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.repository.CourseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();

            for (int i = 0; i < 20; i++) {
                Course c = new Course();
                c.setName("Angular + Spring " + (i + 1));
                c.setCategory(i % 2 == 0 ? Category.BACKEND : Category.FRONTEND);

                Lesson l = new Lesson();
                l.setName("Controllers " + (i + 1));
                l.setYoutubeUrl("2d2v4g0a1xM");
                l.setCourse(c);
                c.getLessons().add(l);

                Lesson l2 = new Lesson();
                l2.setName("Pagination " + (i + 1));
                l2.setYoutubeUrl("3d2v4g0a1xM");
                l2.setCourse(c);
                c.getLessons().add(l2);

                courseRepository.save(c);
            }

        };

    }

}
