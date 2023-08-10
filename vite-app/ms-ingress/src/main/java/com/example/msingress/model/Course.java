package com.example.msingress.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isActive;


    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    public void removeStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }
}
