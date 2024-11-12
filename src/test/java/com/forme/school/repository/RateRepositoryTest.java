package com.forme.school.repository;

import com.forme.school.entity.Rate;
import com.forme.school.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
class RateRepositoryTest {
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldGetAllRates() {
        List<Rate> rates = rateRepository.findAll();

        assertEquals(4, rates.size());
        assertEquals(19.8, rates.get(0).getRate_v(), 0.001);
    }

    @Test
    void shouldSaveRate() {
        Rate rate = new Rate();

        Student student = studentRepository.findById(1L).get();

        assertNotNull(student.getId());

        rate.setRate_v(17.4);
        rate.setRate_date(LocalDateTime.now());
        rate.setStudent(student);

        Rate savedRate = rateRepository.save(rate);

        assertNotNull(savedRate.getId());
        assertEquals(17.4, savedRate.getRate_v(), 0.001);
        assertEquals(LocalDateTime.now().withNano(0), savedRate.getRate_date().withNano(0));
        assertEquals(student, savedRate.getStudent());
    }

    @Test
    void shouldUpdateRate() {
        Rate rate = rateRepository.findById(1L).get();
        rate.setRate_date(LocalDateTime.now());
        rate.setRate_v(11.5);

        Rate updatedRate = rateRepository.save(rate);

        assertEquals(11.5, updatedRate.getRate_v(), 0.001);

    }

    @Test
    void shouldDeleteRate() {
        rateRepository.deleteById(3L);

        Optional<Rate> rate = rateRepository.findById(3L);

        assertFalse(rate.isPresent());
    }
}