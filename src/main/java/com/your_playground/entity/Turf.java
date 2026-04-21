package com.your_playground.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "turfs")
public class Turf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long turfId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String sportType;

    @Column(nullable = false)
    private Double pricePerHour;

    // 🔥 OWNER RELATION
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Long getTurfId() {
        return turfId;
    }

    public void setTurfId(Long turfId) {
        this.turfId = turfId;
    }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
