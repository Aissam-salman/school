package com.forme.school.service;

import com.forme.school.entity.Course;
import com.forme.school.entity.Student;
import com.forme.school.repository.CourseRepository;
import com.forme.school.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolManagementService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public SchoolManagementService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    @Transactional
    public void registerStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getStudents().contains(student)) {
            throw new RuntimeException("Student is already enrolled in this course.");
        }

        course.getStudents().add(student);
        courseRepository.save(course);
    }
}
