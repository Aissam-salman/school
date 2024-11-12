package com.forme.school.controller;

import com.forme.school.entity.Student;
import com.forme.school.service.SchoolManagementService;
import com.forme.school.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final SchoolManagementService schoolManagementService;

    public StudentController(StudentService studentService, SchoolManagementService schoolManagementService) {
        this.studentService = studentService;
        this.schoolManagementService = schoolManagementService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createOrUpdate(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> registerStudentToCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            schoolManagementService.registerStudentToCourse(studentId, courseId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentUpdate) {
        Student existingStudent = studentService.findById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        studentUpdate.setId(existingStudent.getId());
        Student updatedStudent = studentService.createOrUpdate(studentUpdate);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (Objects.nonNull(student)) {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
