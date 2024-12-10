package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Query("SELECT s FROM Driver s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(s.licenseNumber AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Driver> searchByKeyword(@Param("keyword") String keyword);

    boolean existsByLicenseNumber(String licenseNumber);
}