package com.forme.school.repository;

import com.forme.school.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Course c JOIN c.students s " +
            "WHERE c.id = :courseId AND s.id = :studentId")
    boolean existsStudentInCourse(@Param("courseId") Long courseId,
                                  @Param("studentId") Long studentId);

    Optional<Course> findByName(String name);
}
