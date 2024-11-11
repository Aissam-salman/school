package com.forme.school.repository;

import com.forme.school.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldGetAllCourses() {
        // Arrange: prepare mes données
        // fait dans le data.sql

        // Act: appel la method que je veux tester
        List<Course> courses = courseRepository.findAll();

        // Assert: vérification
        // je devrais avoir 3 cours dans ma table
        assertEquals(3, courses.size());
        assertEquals("Mathematics", courses.get(0).getName());
    }

    @Test
    void shouldGetCourseById() {
        // Arrange : ok in data.sql
        //Act
        Course course = courseRepository.findById(1L).get();

        //Assert
        assertEquals("Mathematics", course.getName());
        assertEquals(1L, course.getId());
        assertEquals("MATH101", course.getCode());
    }

    @Test
    void shouldSaveCourse() {
        //Arrange
        Course course = new Course();
        course.setName("Informatics");
        course.setCode("IT101");

        //Act
        Course saveCourse = courseRepository.save(course);

        //Assert
        assertNotNull(saveCourse.getId());
        assertEquals("Informatics", saveCourse.getName());
        assertEquals("IT101", saveCourse.getCode());
    }

    @Test
    void shouldUpdateCourse() {
        Course course = courseRepository.findById(1L).get();
        course.setName("Maths");

        Course updatedCourse = courseRepository.save(course);

        assertEquals("Maths", updatedCourse.getName());
    }

    @Test
    void shouldDeleteCourse() {
         courseRepository.deleteById(3L);

         Optional<Course> course = courseRepository.findById(3L);

         assertFalse(course.isPresent());
    }

}