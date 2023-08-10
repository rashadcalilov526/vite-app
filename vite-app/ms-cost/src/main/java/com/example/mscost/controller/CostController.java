// CostController.java
package com.example.mscost.controller;

import com.example.mscost.client.ImageServiceClient;
import com.example.mscost.dto.CostRequest;
import com.example.mscost.dto.CostResponse;
import com.example.mscost.dto.CostUpdateRequest;
import com.example.mscost.model.Cost;
import com.example.mscost.service.CostService;
import com.example.mscost.mapper.CostMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/costs")
@RequiredArgsConstructor
public class CostController {

    private final CostService costService;
    private final CostMapper costMapper;
    private final ImageServiceClient imageServiceClient;


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCost(@RequestBody CostUpdateRequest costRequest) {

        // Return the updated cost as a response
         costService.updateCost(costRequest);
    }
    @PutMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCost(
            @RequestBody CostRequest costRequest) {
        costService.createCost( costMapper.mapRequestToEntityCreate(costRequest));
    }
    @GetMapping("/{id}")
    public CostResponse getCostById(@PathVariable Long id) {
        Cost cost = costService.getCostById(id);
        return costMapper.mapEntityToResponse(cost);
    }

    @GetMapping("/all")
    public List<CostResponse> getAllCosts() {
        List<Cost> costs = costService.getAllCosts();
        return costs.stream()
                .map(costMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

}
