package com.vitorhcoelho.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor 
public class CourseController {

  private CourseRepository courseRepository;
    
  @GetMapping
  public @ResponseBody List<Course> list() {
    return courseRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> listById(@PathVariable @NotNull @Positive Long id) {
    return courseRepository.findById(id).map(recordFound -> ResponseEntity.ok().body(recordFound)).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course create(@RequestBody @Valid Course course) {
    return courseRepository.save(course);
  }

  @PutMapping("/{id}")
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody Course course) {
    return courseRepository.findById(id).map(recordFound -> {
      recordFound.setName(course.getName());
      recordFound.setCategory(course.getCategory());
      Course updated = courseRepository.save(recordFound);

      return ResponseEntity.ok().body(updated);
    }).orElse(ResponseEntity.notFound().build());
  };

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
    return courseRepository.findById(id).map(recordFound -> {
      courseRepository.deleteById(id);
      return ResponseEntity.noContent().<Void>build();
    }).orElse(ResponseEntity.notFound().build());
  }

}