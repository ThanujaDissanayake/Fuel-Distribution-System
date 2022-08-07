package com.dissanayake.fuel.repository;

import com.dissanayake.fuel.model.FuelStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AllocationRepository extends JpaRepository<FuelStock,String> {
    @Query(value = "SELECT * FROM fuelstock WHERE type = ?1", nativeQuery = true)
    FuelStock findByName(String type);
}
