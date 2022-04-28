package com.wictro.cacttus.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_zones")
public class ParkingZone {
    @Id
    @Column(name = "id")
    private long id;

    @JoinColumn(referencedColumnName = "id", name = "city")
    @ManyToOne(cascade = CascadeType.ALL)
    private City city;

    @Column(name = "name")
    private String name;

    @Column(name = "location_lat")
    private String locationLatitude;

    @Column(name = "location_lng")
    private String longitudeLatitude;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "parkingZone", fetch = FetchType.LAZY)
    private List<ParkingSlot> parkingSlots;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLongitudeLatitude() {
        return longitudeLatitude;
    }

    public void setLongitudeLatitude(String longitudeLatitude) {
        this.longitudeLatitude = longitudeLatitude;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
