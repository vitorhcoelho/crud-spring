package com.vitorhcoelho.controller;

import java.util.List;

import com.vitorhcoelho.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vitorhcoelho.model.Course;
import com.vitorhcoelho.repository.CourseRepository;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }
    
  @GetMapping
  public @ResponseBody List<Course> list() {
    return courseService.list();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
    return courseService.findById(id).map(recordFound -> ResponseEntity.ok().body(recordFound)).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course create(@RequestBody @Valid Course course) {
    return courseService.create(course);
  }

  @PutMapping("/{id}")
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody Course course) {
    return courseService.update(id, course).map(recordFound -> ResponseEntity.ok().body(recordFound)).orElse(ResponseEntity.notFound().build());
  };

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
    if(courseService.delete(id)){
      return ResponseEntity.noContent().<Void>build();
    }
    return ResponseEntity.notFound().build();
  }

}