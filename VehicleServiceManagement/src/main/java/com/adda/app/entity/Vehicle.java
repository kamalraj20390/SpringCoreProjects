package com.adda.app.entity;

import com.adda.app.enums.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotBlank(message = "Registration number is required")
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @NotBlank(message = "Make is required")
    @Column(nullable = false)
    private String make;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Min(value = 1886, message = "Year must be 1886 or later")
    @Max(value = 2100, message = "Year seems too far in the future")
    @Column(name = "manufacturing_year", nullable = false)
    private Integer manufacturingYear;

    @Min(value = 0, message = "Odometer must be 0 or more")
    @Column(nullable = false)
    private Integer odometer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceBooking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobCard> jobCards = new ArrayList<>();
}
