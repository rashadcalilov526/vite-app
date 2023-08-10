package com.example.msingress.dto.response;

import com.example.msingress.model.Course;
import com.example.msingress.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseStudentsResponse {

    private Course course;
    private List<Student> students;

}
