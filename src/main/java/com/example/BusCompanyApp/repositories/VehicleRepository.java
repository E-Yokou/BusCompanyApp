package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE " +
            "LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.vehicleNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Vehicle> searchByKeyword(@Param("keyword") String keyword);
}
