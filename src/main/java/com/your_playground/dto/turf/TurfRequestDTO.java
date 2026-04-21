package com.your_playground.dto.turf;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TurfRequestDTO {

    @NotBlank
    private String location;

    @NotBlank
    private String sportType;

    @NotNull
    private Double pricePerHour;

    @NotNull
    private Long ownerId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}