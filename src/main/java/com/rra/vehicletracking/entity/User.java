package com.rra.vehicletracking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String names;

    @Column(nullable = false, unique = true)
    private  String email;

    @Column(nullable = false, unique = true)
    private  String phone;

    @Column(nullable = false, unique = true, name = "national_id")
    private  String nationalID;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private  Set<String> roles= new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PlateNumber> plateNumbers = new HashSet<>();

    @Column(nullable = false)
    private String address;

    private String addRole(String role){
        this.roles.add(role);
    }

    private  boolean hasRole(String role){
        return  this.roles.contains(role);
    }
}
