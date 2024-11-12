package com.forme.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forme.school.entity.Course;
import com.forme.school.entity.Rate;
import com.forme.school.entity.Student;
import com.forme.school.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateController.class)
class RateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateService rateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllRates() throws Exception {

        Course course = new Course(1L, "Maths", "MATH101");
        Course course2 = new Course(2L, "Economics", "ECO101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());
        Rate rate2 = new Rate(2L, course2, student, 13.7, LocalDateTime.now());

        when(rateService.findAll()).thenReturn(List.of(rate1, rate2));

        mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate_v").value(15.5))
                .andExpect(jsonPath("$[1].rate_v").value(13.7));
    }

    @Test
    void shouldReturnRateById() throws Exception {
        Course course = new Course(1L, "Maths", "MATH101");

        Student student = new Student(1L, "John", "Doe");

        Rate rate1 = new Rate(1L, course, student, 15.5, LocalDateTime.now());

        when(rateService.findRateById(1L)).thenReturn(rate1);

        mockMvc.perform(get("/api/rates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rate_v").value(15.5));
    }

    @Test
    void shouldReturnCreatedRate() throws Exception {
        Course course = new Course(1L, "Maths", "MATH101");
        Student student = new Student(1L, "John", "Doe");
        Rate rate = new Rate(5L, course, student, 15.5, LocalDateTime.now());


        when(rateService.create(any(Rate.class))).thenReturn(rate);

        mockMvc.perform(post("/api/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(rate.getId()))
                .andExpect(jsonPath("$.rate_v").value(rate.getRate_v()));

    }

    @Test
    void shouldReturnUpdatedRate() throws Exception {
        double newValue = 18.5;
        Long rateId = 5L;
        Course course = new Course(1L, "Maths", "MATH101");
        Student student = new Student(1L, "John", "Doe");
        Rate rate = new Rate(rateId, course, student, 15.5, LocalDateTime.now());

        Rate updatedRate = new Rate(rateId, course, student, newValue, LocalDateTime.now());

        when(rateService.findRateById(rateId)).thenReturn(rate);
        when(rateService.updateValue(rateId, newValue)).thenReturn(updatedRate);

        mockMvc.perform(put("/api/rates/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newValue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rateId))
                .andExpect(jsonPath("$.rate_v").value(newValue));
    }

    @Test
    void shouldDeleteRate() throws Exception {

        Course course = new Course(1L, "Maths", "MATH101");
        Student student = new Student(1L, "John", "Doe");
        Rate rate = new Rate(5L, course, student, 15.5, LocalDateTime.now());

        when(rateService.findRateById(rate.getId())).thenReturn(rate);

        mockMvc.perform(delete("/api/rates/5"))
                .andExpect(status().isNoContent());
    }

}