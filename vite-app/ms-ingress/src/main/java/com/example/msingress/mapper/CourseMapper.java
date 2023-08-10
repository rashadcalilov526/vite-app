package com.example.msingress.mapper;

import com.example.msingress.dto.request.CourseRequest;
import com.example.msingress.dto.response.CourseResponse;
import com.example.msingress.model.Course;
import com.example.msingress.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseMapper {
    private  final StudentRepository studentRepository;

    public static Course mapRequestToEntity(CourseRequest request) {
        return Course.builder()
                .name(request.getName())
                .isActive(request.getIsActive())
                .build();
    }
    public static void updateCourse(CourseRequest request, Course entity) {
        entity.setName(request.getName());
        entity.setIsActive(request.getIsActive());
    }
}
