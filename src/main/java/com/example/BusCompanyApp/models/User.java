package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "userLogin", nullable = false, unique = true)
    private String userLogin;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Transient
    @Column(name = "userPasswordConfirm")
    private String userPasswordConfirm;

    @Column(name = "userEmail", nullable = false, unique = true)
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRoleId")
    private Role userRole;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole=" + (userRole != null ? userRole.getRoleId() : null) +
                '}';
    }
}