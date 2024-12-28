package com.example.mongodb_springboot.controller;

import com.example.mongodb_springboot.model.Course;
import com.example.mongodb_springboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestParam("title") String title,
                                               @RequestParam("price") double price,
                                               @RequestParam("image") MultipartFile imageFile) {
        Course course = new Course();
        course.setTitle(title);
        course.setPrice(price);

        Course createdCourse = courseService.createCourse(course, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String id,
                                               @RequestParam("title") String title,
                                               @RequestParam("price") double price,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Course updatedCourse = new Course();
        updatedCourse.setTitle(title);
        updatedCourse.setPrice(price);

        Course course = courseService.updateCourse(id, updatedCourse, imageFile);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
