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

    public Student createOrUpdate(final Student student) {
        return studentRepository.save(student);
    }

    public Student findById(final Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }


    public void delete(final Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Student not found");
        }
    }
}
