package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query("SELECT s FROM Route s WHERE " +
            "LOWER(s.startLocation) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.endLocation) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(s.description AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Route> searchByKeyword(@Param("keyword") String keyword);
}