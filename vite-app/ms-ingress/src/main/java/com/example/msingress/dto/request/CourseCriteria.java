package com.example.msingress.dto.request;

import com.example.msingress.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCriteria {
    private String name;
    private Set<Student> students;

}