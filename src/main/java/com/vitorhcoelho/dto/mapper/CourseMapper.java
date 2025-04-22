package com.vitorhcoelho.dto.mapper;

import com.vitorhcoelho.dto.CourseDTO;
import com.vitorhcoelho.enums.Category;
import com.vitorhcoelho.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
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
