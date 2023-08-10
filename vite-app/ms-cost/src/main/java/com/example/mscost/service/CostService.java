// CostService.java
package com.example.mscost.service;

import com.example.mscost.client.ImageServiceClient;
import com.example.mscost.dto.CostRequest;
import com.example.mscost.dto.CostUpdateRequest;
import com.example.mscost.model.Cost;
import com.example.mscost.repository.CostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CostService {

    private final CostRepository costRepository;

   private final ImageServiceClient imageServiceClient;

    public Cost createCost(Cost cost) {

        return costRepository.save(cost);
    }
    public void updateCost(CostUpdateRequest costUpdateRequest){
        Cost cost=costRepository.findById(costUpdateRequest.getId()).orElseThrow(()->new RuntimeException("Cost not found"));
        cost.setCostName(costUpdateRequest.getCostName());
        cost.setCostDate(costUpdateRequest.getCostDate());
        cost.setCostValue(costUpdateRequest.getCostValue());
        cost.setDescription(costUpdateRequest.getDescription());
        cost.setImageId(1L);
        costRepository.save(cost);
        //cost.setImageId(imageServiceClient.uploadImage(costUpdateRequest.getMultipartFile()));
    }

    public Cost getCostById(Long id) {
        return costRepository.findById(id).orElseThrow(()->new RuntimeException("Cost not found"));
    }

    public List<Cost> getAllCosts() {
        return costRepository.findAll();
    }

    // Add other service methods for updating, deleting, etc.
}
