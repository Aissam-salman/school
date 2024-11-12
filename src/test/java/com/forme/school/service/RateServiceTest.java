package com.forme.school.service;

import com.forme.school.entity.Course;
import com.forme.school.entity.Rate;
import com.forme.school.entity.Student;
import com.forme.school.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RateServiceTest {
    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;

    @Test
    void shouldReturnAllRates() {
        Course course = new Course(1L, "Maths", "MATH101");
        Course course2 = new Course(2L, "Economics", "ECO101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());
        Rate rate2 = new Rate(2L, course2, student, 13.7, LocalDateTime.now());

        when(rateRepository.findAll()).thenReturn(List.of(rate1, rate2));

        List<Rate> rates = rateService.findAll();

        assertThat(rates).hasSize(2).containsExactly(rate1, rate2);
    }

    @Test
    void shouldReturnRateById() {
        Course course = new Course(1L, "Maths", "MATH101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());

        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate1));

        Rate rateById = rateService.findRateById(1L);

        assertThat(rateById).isEqualTo(rate1);
    }

    @Test
    void shouldReturnRateOnSave() {

        Course course = new Course(1L, "Maths", "MATH101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());
        when(rateRepository.save(rate1)).thenReturn(rate1);

        Rate savedRate = rateService.create(rate1);

        assertThat(savedRate).isEqualTo(rate1);
    }

    @Test
    void shouldReturnRateOnUpdate() {
        Course course = new Course(1L, "Maths", "MATH101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());
        Rate updatedRate = new Rate(1L, course, student, 13.7, LocalDateTime.now());

        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate1));
        when(rateRepository.save(rate1)).thenReturn(updatedRate);

        Rate savedRate = rateService.updateValue(1L, 13.7);

        assertThat(savedRate).isEqualTo(updatedRate);
    }

    @Test
    void shouldDeleteRate() {
        rateService.delete(1L);
        verify(rateRepository).deleteById(1L);
    }

}