package com.wictro.cacttus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_zones")
public class ParkingZone {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(referencedColumnName = "id", name = "city")
    @ManyToOne()
    private City city;

    @Column(name = "name")
    private String name;

    @Column(name = "location_lat")
    private String locationLatitude;

    @Column(name = "location_lng")
    private String locationLongitude;

    @Column(name = "is_operating")
    private Boolean isOperating;

    @JsonIgnore
    @Column(name = "is_active")
    private Boolean isActive;

    @JsonProperty("isOperating")
    public Boolean getOperating() {
        return isOperating;
    }

    public void setOperating(Boolean operating) {
        isOperating = operating;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "parkingZone", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingSlot> parkingSlots;

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String longitudeLatitude) {
        this.locationLongitude = longitudeLatitude;
    }

    @JsonIgnore
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
