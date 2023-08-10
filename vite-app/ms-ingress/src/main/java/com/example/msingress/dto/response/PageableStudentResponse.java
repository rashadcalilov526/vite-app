package com.example.msingress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableStudentResponse {
    private List<StudentResponse> student;
    private long totalElements;
    private int totalPages;
    private boolean hasNextPage;
}
