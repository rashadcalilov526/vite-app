package com.example.msingress.service.util;

import com.example.msingress.model.Course;
import com.example.msingress.model.Student;

import java.util.Optional;

public class Utils {
    public static Course fecthCourse(Course course) {
        return Optional.ofNullable(course)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    public static Student fecthStudent(Student student) {
        return Optional.ofNullable(student)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
