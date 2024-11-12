package com.forme.school.controller;

import com.forme.school.entity.Course;
import com.forme.school.entity.Student;
import com.forme.school.service.CourseService;
import com.forme.school.service.SchoolManagementService;
import com.forme.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private SchoolManagementService schoolManagementService;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldReturnAllStudents() throws Exception {
        Student student1 = new Student(1L, "Doe", "john");
        Student student2 = new Student(2L, "foo", "boo");

        when(studentService.findAll()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Doe"))
                .andExpect(jsonPath("$[1].firstName").value("foo"));
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        Student student1 = new Student(1L, "Doe", "john");

        when(studentService.findById(1L)).thenReturn(student1);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Doe"))
                .andExpect(jsonPath("$.lastName").value("john"));
    }

    @Test
    void shouldReturnCreateStudent() throws Exception {
        String json = """
                {
                "firstName": "Loo",
                "lastName": "Doo"
                }
                """;


        when(studentService.createOrUpdate(any(Student.class)))
                .thenReturn(new Student(1L, "Loo", "Doo"));

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Loo"))
                .andExpect(jsonPath("$.lastName").value("Doo"));
    }

    @Test
    void shouldReturnUpdateStudent() throws Exception {
        String json = """
                {
                "firstName": "John",
                "lastName": "Doo"
                }
                """;

        Student existingStudent = new Student(1L, "Arde", "Doo");
        Student updatedStudent = new Student(1L, "John", "Doo");

        when(studentService.findById(1L)).thenReturn(existingStudent);
        when(studentService.createOrUpdate(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldAddStudentToCourse() throws Exception {
        Student student1 = new Student(1L, "Doe", "john");
        Set<Student> members = new HashSet<>();
        members.add(student1);
        Course course1 = new Course(1L, "Java", "JAVA101", members);

        when(courseService.findById(1L)).thenReturn(course1);
        when(studentService.findById(1L)).thenReturn(student1);

        when(schoolManagementService.registerStudentToCourse(student1.getId(), course1.getId())).thenReturn(course1);

        mockMvc.perform(post("/api/students/1/courses/1"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        Student student1 = new Student(1L, "Doe", "john");

        when(studentService.findById(1L)).thenReturn(student1);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }

}