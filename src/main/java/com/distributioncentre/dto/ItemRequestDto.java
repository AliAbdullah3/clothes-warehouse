package com.distributioncentre.dto;

import com.distributioncentre.model.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemRequestDto {
    @NotBlank(message = "Item name is required")
    private String name;

    @NotNull(message = "Brand is required")
    private Brand brand;

    // Constructors
    public ItemRequestDto() {}

    public ItemRequestDto(String name, Brand brand) {
        this.name = name;
        this.brand = brand;
    }

    // Getters and Setters
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
}
