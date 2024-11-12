package com.forme.school.service;

import com.forme.school.entity.Course;
import com.forme.school.entity.Student;
import com.forme.school.repository.CourseRepository;
import com.forme.school.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SchoolManagementServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SchoolManagementService schoolManagementService;

    @Test
    void shouldReturnCourseWithAddedStudents() {
        Long studentId = 1L;
        Long courseId = 1L;
        Student student = new Student(studentId, "John", "Doe");
        Course course = new Course(courseId, "Maths", "MATH101");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course result = schoolManagementService.registerStudentToCourse(studentId, courseId);

        assertNotNull(result);
        assertTrue(result.getStudents().contains(student));
        verify(courseRepository).save(course);
    }

}