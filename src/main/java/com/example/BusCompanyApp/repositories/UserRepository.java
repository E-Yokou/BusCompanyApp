package com.example.BusCompanyApp.repositories;


import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLogin(String userLogin);

    @Query("SELECT u FROM User u JOIN FETCH u.userRole WHERE u.userLogin = :username")
    Optional<User> findByUserLoginWithRole(@Param("username") String username);

    @Query("SELECT t FROM Trip t WHERE t.departureLocation = :departureLocation AND t.destinationLocation = :destinationLocation")
    List<Trip> findTripsByLocations(@Param("departureLocation") String departureLocation,
                                    @Param("destinationLocation") String destinationLocation);

    @Query("SELECT DISTINCT t.departureLocation FROM Trip t")
    List<String> findDistinctDepartureLocations();

    @Query("SELECT DISTINCT t.destinationLocation FROM Trip t")
    List<String> findDistinctArrivalLocations();
}