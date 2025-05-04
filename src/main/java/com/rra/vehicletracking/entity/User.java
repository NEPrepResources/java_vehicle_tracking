package com.rra.vehicletracking.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String names;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true, name = "national_id")
    private String nationalID;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PlateNumber> plateNumbers = new HashSet<>();

    @Column(nullable = false)
    private String address;

    public User() {
    }

    public User(Long id, String names, String email, String phone, String nationalID,
                String password, Set<String> roles, Set<PlateNumber> plateNumbers, String address) {
        this.id = id;
        this.names = names;
        this.email = email;
        this.phone = phone;
        this.nationalID = nationalID;
        this.password = password;
        this.roles = roles;
        this.plateNumbers = plateNumbers;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<PlateNumber> getPlateNumbers() {
        return plateNumbers;
    }

    public void setPlateNumbers(Set<PlateNumber> plateNumbers) {
        this.plateNumbers = plateNumbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addPlateNumber(PlateNumber plateNumber) {
        plateNumbers.add(plateNumber);
        plateNumber.setUser(this);
    }

    public void removePlateNumber(PlateNumber plateNumber) {
        plateNumbers.remove(plateNumber);
        plateNumber.setUser(null);
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public boolean hasRole(String role) {
        return this.roles.contains(role);
    }
}