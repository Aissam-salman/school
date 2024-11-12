package com.forme.school.repository;

import com.forme.school.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldGetAllStudents(){
        List<Student> students = studentRepository.findAll();

        assertEquals(3, students.size());
        assertEquals("Alice", students.get(0).getFirstName());
    }

    @Test
    void shouldGetStudentById(){
        Student student = studentRepository.findById(1L).get();

        assertEquals("Alice", student.getFirstName());
        assertEquals("Martin", student.getLastName());
    }

    @Test
    void shouldSaveStudent(){
        Student student = new Student();
        student.setFirstName("Sam");
        student.setLastName("Dupont");

        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals("Sam", savedStudent.getFirstName());
        assertEquals("Dupont", savedStudent.getLastName());
    }

    @Test
    void shouldUpdateStudent(){
        Student student = studentRepository.findById(1L).get();
        student.setFirstName("Mela");

        Student updatedStudent = studentRepository.save(student);

        assertEquals("Mela", updatedStudent.getFirstName());
    }

    @Test
    void shouldDeleteStudent(){
        studentRepository.deleteById(3L);

        Optional<Student> studentOptional = studentRepository.findById(3L);

        assertFalse(studentOptional.isPresent());
    }
}