package com.example.mongodb_springboot.service;

import com.example.mongodb_springboot.model.Course;
import com.example.mongodb_springboot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    private static final String IMAGE_DIR = "uploads/images/";

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course createCourse(Course course, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            course.setImage(imagePath);
        }
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(String id, Course updatedCourse, MultipartFile imageFile) {
        Optional<Course> existingCourseOpt = courseRepository.findById(id);

        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();

            // Update title and price
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setPrice(updatedCourse.getPrice());

            // Update image if a new file is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                deleteImageFile(existingCourse.getImage());
                String imagePath = saveImage(imageFile);
                existingCourse.setImage(imagePath);
            }

            return courseRepository.save(existingCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    @Override
    public void deleteCourse(String id) {
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();

            // Delete the associated image file if it exists
            deleteImageFile(course.getImage());

            // Delete the course
            courseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    // Helper method to save an image to the file system
    private String saveImage(MultipartFile imageFile) {
        try {
            Path imageDirPath = Paths.get("C:\\Users\\samib\\Desktop\\9antra.tn\\mongodb-springboot\\uploads");
            if (!Files.exists(imageDirPath)) {
                Files.createDirectories(imageDirPath);
            }
            String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path imagePath = imageDirPath.resolve(imageFileName);
            imageFile.transferTo(imagePath.toFile());
            return "/uploads/" + imageFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }



    // Helper method to delete an image file from the file system
    private void deleteImageFile(String imagePath) {
        if (imagePath != null) {
            try {
                Path path = Paths.get(imagePath);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }
    }
}
