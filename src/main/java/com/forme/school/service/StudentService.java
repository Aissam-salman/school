package com.forme.school.service;

import com.forme.school.entity.Student;
import com.forme.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(final Student student) {
        return studentRepository.save(student);
    }

    public Student findById(final Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student update(final Long studentId, final Student studentUpdate) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (studentUpdate.getFirstName() != null && !studentUpdate.getFirstName().isEmpty()) {
            student.setFirstName(studentUpdate.getFirstName());
        }
        if (studentUpdate.getLastName() != null && !studentUpdate.getLastName().isEmpty()) {
            student.setLastName(studentUpdate.getLastName());
        }
        if (studentUpdate.getCourses() != null) {
            student.setCourses(studentUpdate.getCourses());
        }
        return studentRepository.save(student);
    }

    public boolean delete(final Long id) {
        try {

            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
