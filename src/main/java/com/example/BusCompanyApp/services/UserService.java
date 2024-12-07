package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Role;
import com.example.BusCompanyApp.models.User;
import com.example.BusCompanyApp.models.UserRegistrationDto;
import com.example.BusCompanyApp.repositories.RoleRepository;
import com.example.BusCompanyApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public User updateUser(Long userId, User userDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User curUser = user.get();
            curUser.setUserLogin(userDetails.getUserLogin());
            curUser.setUserPassword(userDetails.getUserPassword());
            curUser.setUserEmail(userDetails.getUserEmail());
            curUser.setUserRole(userDetails.getUserRole());
            return userRepository.save(curUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

