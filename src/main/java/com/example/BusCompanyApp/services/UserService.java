package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Role;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import com.example.BusCompanyApp.models.UserRegistrationDto;
import com.example.BusCompanyApp.repositories.RoleRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import com.example.BusCompanyApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        Role role = roleRepository.findById(userRegistrationDto.getUserRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setUserLogin(userRegistrationDto.getUserLogin());
        user.setUserPassword(passwordEncoder.encode(userRegistrationDto.getUserPassword()));
        user.setUserEmail(userRegistrationDto.getUserEmail());
        user.setUserRole(role);

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<Trip> findTrips(String departureLocation, String destinationLocation) {
        return userRepository.findTripsByLocations(departureLocation, destinationLocation);
    }

    public List<String> getDepartureLocationsByQuery(String query) {
        return tripRepository.findDepartureLocationsByQuery(query);
    }

    public List<String> getArrivalLocationsByQuery(String query) {
        return tripRepository.findArrivalLocationsByQuery(query);
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByUserLogin(userDetails.getUsername());
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }
}

