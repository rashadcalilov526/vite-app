// CostRepository.java
package com.example.mscost.repository;

import com.example.mscost.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {
    // You can add custom query methods if needed
}
