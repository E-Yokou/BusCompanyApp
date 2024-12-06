package com.example.BusCompanyApp.repositories;


import com.example.BusCompanyApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
