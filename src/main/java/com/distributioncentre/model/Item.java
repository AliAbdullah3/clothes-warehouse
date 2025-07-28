package com.distributioncentre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Brand is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Brand brand;

    @NotNull(message = "Year of creation is required")
    @Min(value = 2022, message = "Year of creation must be more than 2021")
    @Column(name = "year_of_creation", nullable = false)
    private Integer yearOfCreation;

    @NotNull(message = "Price is required")
    @Min(value = 1001, message = "Price must be more than 1000")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private Integer quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribution_centre_id")
    private DistributionCentre distributionCentre;

    // Constructors
    public Item() {}

    public Item(String name, Brand brand, Integer yearOfCreation, Double price, Integer quantity) {
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(Integer yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public DistributionCentre getDistributionCentre() {
        return distributionCentre;
    }

    public void setDistributionCentre(DistributionCentre distributionCentre) {
        this.distributionCentre = distributionCentre;
    }
}
