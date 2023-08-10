package com.example.msingress.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollRequest {
    private String studentNumber;
    private Long courseId;

    // Getters and setters (or Lombok annotations)
}
