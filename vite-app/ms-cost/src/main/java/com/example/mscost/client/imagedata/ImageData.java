package com.example.mscost.client.imagedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {

    private Long id;

    private String name;
    private String type;

    private byte[] imageData;

   }