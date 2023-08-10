// CostMapper.java
package com.example.mscost.mapper;

import com.example.mscost.client.ImageServiceClient;
import com.example.mscost.dto.CostRequest;
import com.example.mscost.dto.CostUpdateRequest;
import com.example.mscost.dto.CostResponse;
import com.example.mscost.model.Cost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CostMapper {
    private final ImageServiceClient imageServiceClient;

    public CostResponse mapEntityToResponse(Cost cost) {
        
        return new CostResponse(
                cost.getId(),
                cost.getCostValue(),
                cost.getCostDate(),
                cost.getCostName(),
                cost.getDescription()
                //imageServiceClient.downloadImage(cost.getImageId())
        );
    }

    public Cost mapRequestToEntity(CostUpdateRequest costUpdateRequest) {
        return Cost.builder()
                .costValue(costUpdateRequest.getCostValue())
                .costDate(costUpdateRequest.getCostDate())
                .costName(costUpdateRequest.getCostName())
                .description(costUpdateRequest.getDescription())
                .imageId(1L)
                //.imageId(imageServiceClient.uploadImage(costUpdateRequest.getMultipartFile()))
                .build();
    }
    public Cost mapRequestToEntityCreate(CostRequest costRequest) {
        return Cost.builder()
                .costValue(costRequest.getCostValue())
                .costDate(costRequest.getCostDate())
                .costName(costRequest.getCostName())
                .description(costRequest.getDescription())
                .imageId(1L)
                //.imageId(imageServiceClient.uploadImage(costRequest.getMultipartFile()))
                .build();
    }
}
