package com.your_playground.dto.turf;

public class TurfResponseDTO {

    private Long turfId;
    private String location;
    private String sportType;
    private Double pricePerHour;

    private Long ownerId;
    private String ownerName;

    // getters setters

    public Long getTurfId() { return turfId; }
    public void setTurfId(Long turfId) { this.turfId = turfId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }

    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}