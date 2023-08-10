package com.example.msingress.mapper;

import com.example.msingress.dto.request.StudentRequest;
import com.example.msingress.dto.response.StudentResponse;
import com.example.msingress.model.Student;

public class StudentMapper {
    public static StudentResponse mapEntityToResponse(Student entity) {
        return StudentResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .number(entity.getNumber())
                .surname(entity.getSurname())
                .build();
    }
    public static Student mapRequestToEntity(StudentRequest request) {
        return Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .surname(request.getSurname())
                .number(request.getNumber())
                .build();
    }
    public static void updateStudent(StudentRequest request, Student entity) {
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setEmail(request.getEmail());
        entity.setNumber(request.getNumber());

    }
}
