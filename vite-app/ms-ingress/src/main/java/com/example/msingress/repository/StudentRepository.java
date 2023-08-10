package com.example.msingress.repository;

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
public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {
    Student findByName(String name);
    Student findByEmail(String email);

    Student findByNumber(String number);
    @Query("SELECT s.name FROM Student s")
    List<String> getAllStudentNames();
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findByCourseId(Long courseId);
//    @Modifying
//    @Query("DELETE FROM courses_students cs WHERE cs.student_id = :studentId AND cs.course_id = :courseId")
//    void deleteAdditionalRelation(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

}
