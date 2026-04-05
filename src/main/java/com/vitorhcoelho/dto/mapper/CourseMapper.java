package com.vitorhcoelho.dto.mapper;

import com.vitorhcoelho.dto.CourseDTO;
import com.vitorhcoelho.dto.LessonDTO;
import com.vitorhcoelho.enums.Category;
import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        List<LessonDTO> lessons = course.getLessons().stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .toList();

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }

        Course course = new Course();

        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }

        course.setName(courseDTO.name());
        course.setCategory(converCategoryValue(courseDTO.category()));

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());

        course.setLessons(lessons);

        return course;
    }

    public Category converCategoryValue(String categoryValue) {
        if (categoryValue == null) {
            return null;
        }

        return switch (categoryValue) {
            case "Backend" -> Category.BACKEND;
            case "Frontend" -> Category.FRONTEND;
            default -> throw new IllegalArgumentException("Invalid category value: " + categoryValue);
        };

    }
}
