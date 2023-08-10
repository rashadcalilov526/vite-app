// CostResponse.java
package com.example.mscost.dto;

import com.example.mscost.client.imagedata.ImageData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostResponse {
    private Long id;
    private Double costValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private  Date costDate;
    private String costName;
    private String description;
//    private ImageData imageData;
}
