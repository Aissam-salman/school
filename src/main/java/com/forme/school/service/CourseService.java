package com.forme.school.service;

import com.forme.school.entity.Course;
import com.forme.school.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createOrUpdate(final Course course) {
        return courseRepository.save(course);
    }

    public Course findById(final Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course findByName(final String name) {
        return courseRepository.findByName(name).orElse(null);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public boolean delete(final Long id) {
        try {
            courseRepository.deleteById(id);
            return true;
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
