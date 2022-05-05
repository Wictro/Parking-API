package com.wictro.cacttus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    public ParkingSlot(ParkingZone parkingZone, boolean isHandicap) {
        this.parkingZone = parkingZone;
        this.isHandicap = isHandicap;
    }

    public ParkingSlot(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(referencedColumnName = "id", name = "parking_zone")
    @ManyToOne()
    private ParkingZone parkingZone;

    @Column(name = "is_handicap")
    private Boolean isHandicap;

    @Column(name = "is_free")
    private Boolean isFree;

    @JsonIgnore
    @Column(name = "is_active")
    private Boolean isActive;

    @JsonIgnore
    @JsonProperty("isActive")
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "parkingSlot")
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(ParkingZone parkingZone) {
        this.parkingZone = parkingZone;
    }

    @JsonProperty("isHandicap")
    public Boolean isHandicap() {
        return isHandicap;
    }

    public void setHandicap(Boolean handicap) {
        isHandicap = handicap;
    }

    @JsonProperty("isFree")
    public Boolean isFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }
}
