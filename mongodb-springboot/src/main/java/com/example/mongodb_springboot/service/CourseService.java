package com.example.mongodb_springboot.service;

import com.example.mongodb_springboot.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CourseService {


    List<Course> getAllCourses();
    Optional<Course> getCourseById(String id);
    Course createCourse(Course course, MultipartFile imageFile);

    Course updateCourse(String id, Course updatedCourse, MultipartFile imageFile);
    void deleteCourse(String id);
}
