package com.clotheswarehouse.dto;

import java.util.List;

public class DistributionCentreDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private List<ItemDto> items;

    // Constructors
    public DistributionCentreDto() {}

    public DistributionCentreDto(Long id, String name, Double latitude, Double longitude, List<ItemDto> items) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
