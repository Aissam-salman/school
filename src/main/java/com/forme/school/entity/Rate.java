package com.forme.school.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private Double rate_v;


    private LocalDateTime rate_date;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Rate() {
    }

    public Rate(Course course, double rate_v) {
        this.course = course;
        this.rate_v = rate_v;
    }

    public Rate(Course course, Student student, Double rate_v, LocalDateTime rate_date) {
        this.course = course;
        this.student = student;
        this.rate_v = rate_v;
        this.rate_date = rate_date;
    }

    public Rate(Long id, Course course, Student student, Double rate_v, LocalDateTime rate_date) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.rate_v = rate_v;
        this.rate_date = rate_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getRate_v() {
        return rate_v;
    }


    public void setRate_v(Double rate_v) {
        this.rate_v = rate_v;
    }

    public LocalDateTime getRate_date() {
        return rate_date;
    }

    public void setRate_date(LocalDateTime rate_date) {
        this.rate_date = rate_date;
    }
}
