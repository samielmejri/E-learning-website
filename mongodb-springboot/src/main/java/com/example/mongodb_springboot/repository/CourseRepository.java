package com.example.mongodb_springboot.repository;

import com.example.mongodb_springboot.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
}