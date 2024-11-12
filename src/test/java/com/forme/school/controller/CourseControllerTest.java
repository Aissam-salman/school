package com.forme.school.controller;

import com.forme.school.entity.Course;
import com.forme.school.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldReturnAllCourses() throws Exception {
        Course c = new Course(1L, "Cuisine", "CUI101");
        Course c2 = new Course(2L, "Economics", "ECO301");

        when(courseService.findAll()).thenReturn(List.of(c, c2));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cuisine"))
                .andExpect(jsonPath("$[1].name").value("Economics"));
    }

    @Test
    void shouldReturnCourseById() throws Exception {
        Course c = new Course(1L, "Cuisine", "CUI101");

        when(courseService.findById(1L)).thenReturn(c);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cuisine"))
                .andExpect(jsonPath("$.code").value("CUI101"));
    }

    @Test
    void shouldReturnCourseByName() throws Exception {
        Course c = new Course(1L, "Cuisine", "CUI101");

        when(courseService.findByName("Cuisine")).thenReturn(c);

        mockMvc.perform(get("/api/courses/name/" + c.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cuisine"))
                .andExpect(jsonPath("$.code").value("CUI101"));

    }

    @Test
    void shouldReturnCreateCourse() throws Exception {
        String json = """
                {
                "id": 1,
                "name": "Cuisine",
                "code": "CUI101"
                }
                """;

        when(courseService.createOrUpdate(any(Course.class)))
                .thenReturn(new Course(1L, "Cuisine", "CUI101"));

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cuisine"))
                .andExpect(jsonPath("$.code").value("CUI101"));
    }

    @Test
    void shouldReturnUpdateCourse() throws Exception {
        String json = """
                {
                "id": 1,
                "name": "Cook",
                "code": "CUI101"
                }
                """;

        Course existingCourse = new Course(1L, "Cuisine", "CUI101");
        Course updatedCourse = new Course(1L, "Cook", "CUI101");

        when(courseService.findById(1L)).thenReturn(existingCourse);
        when(courseService.createOrUpdate(any(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cook"))
                .andExpect(jsonPath("$.code").value("CUI101"));

        verify(courseService).findById(1L);
        verify(courseService).createOrUpdate(any(Course.class));
    }

    @Test
    void shouldDeleteCourse() throws Exception {

        Course existingCourse = new Course(1L, "Cuisine", "CUI101");

        when(courseService.findById(1L)).thenReturn(existingCourse);

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }
}