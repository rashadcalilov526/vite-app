package com.example.msingress.repository;

import com.example.msingress.model.Course;
import com.example.msingress.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface CourseRepository extends JpaRepository<Course,Long>, JpaSpecificationExecutor<Course> {
    Course findByName(String name);
    @Query("SELECT c FROM Course c WHERE c.isActive = true")
    List<Course> findByIsActive();
    boolean existsByName(String name);
//    @Modifying
//    @Query("DELETE FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId")
//    void deleteRelation(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

}
