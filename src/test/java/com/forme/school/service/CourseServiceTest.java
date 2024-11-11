package com.forme.school.service;

import com.forme.school.entity.Course;
import com.forme.school.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldReturnAllCourses() {
        Course course = new Course(1L,"Cuisine","CUI101");
        Course course2 = new Course(2L,"Economics","ECO101");

        // define return of mock (fake repo method return for testing service)
        when(courseRepository.findAll()).thenReturn(List.of(course,course2));

        List<Course> courses = courseService.findAll();

        assertThat(courses).hasSize(2).containsExactly(course,course2);
    }

    @Test
    void shouldReturnCourseById() {
        Course course = new Course(1L,"Cuisine","CUI101");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course courseById = courseService.findById(1L);

        assertThat(courseById).isEqualTo(course);
    }

    @Test
    void shouldReturnCourseByName() {}

    @Test
    void shouldReturnCourseOnSaveOrUpdate(){

    }
}