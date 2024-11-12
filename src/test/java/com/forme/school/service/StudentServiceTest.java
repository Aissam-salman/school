package com.forme.school.service;

import com.forme.school.entity.Student;
import com.forme.school.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldReturnAllStudents() {
        Student stu1 = new Student(1L, "Karim", "Elawi");
        Student stu2 = new Student(2L, "Lina", "Dual");

        when(studentRepository.findAll()).thenReturn(List.of(stu1,stu2));

        List<Student> students = studentService.findAll();

        assertThat(students).hasSize(2).containsExactly(stu1, stu2);
    }

    @Test
    void shouldReturnStudentById() {
        Student stu1 = new Student(1L, "Karim", "Elawi");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(stu1));

        Student studentById = studentService.findById(1L);

        assertThat(studentById).isEqualTo(stu1);
    }

    @Test
    void shouldReturnStudentOnSaveOrUpdate(){
        Student stu1 = new Student(1L, "Karim", "Elawi");
        when(studentRepository.save(stu1)).thenReturn(stu1);

        Student savedStudent = studentService.createOrUpdate(stu1);

        assertThat(savedStudent).isEqualTo(stu1);
    }

    @Test
    void shouldDeleteStudent(){
        studentService.delete(1L);

        verify(studentRepository).deleteById(1L);
    }

}