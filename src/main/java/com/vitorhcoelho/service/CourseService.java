package com.vitorhcoelho.service;

import com.vitorhcoelho.dto.CourseDTO;
import com.vitorhcoelho.dto.CoursePageDTO;
import com.vitorhcoelho.dto.mapper.CourseMapper;
import com.vitorhcoelho.exception.RecordNotFoundException;
import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        Page<Course> pageCourse = this.courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());

        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }

    public CourseDTO listById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@NotNull @Valid CourseDTO courseDTO) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id).map(recordFound -> {
            Course course = courseMapper.toEntity(courseDTO);
            recordFound.setName(courseDTO.name());
            recordFound.setCategory(courseMapper.converCategoryValue(courseDTO.category()));
            recordFound.getLessons().clear();
            course.getLessons().forEach(lesson -> {
                recordFound.getLessons().add(lesson);
            });
            return courseMapper.toDTO(courseRepository.save(recordFound));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
