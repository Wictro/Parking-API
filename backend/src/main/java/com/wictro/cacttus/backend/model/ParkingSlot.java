package com.wictro.cacttus.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {
    @Id
    @Column(name = "id")
    private long id;

    @JoinColumn(referencedColumnName = "id", name = "parking_zone")
    @ManyToOne(cascade = CascadeType.ALL)
    private ParkingZone parkingZone;

    @Column(name = "is_handicap")
    private boolean isHandicap;

    @Column(name = "is_free")
    private boolean isFree;

    @OneToMany(mappedBy = "parkingSlot")
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(ParkingZone parkingZone) {
        this.parkingZone = parkingZone;
    }

    public boolean isHandicap() {
        return isHandicap;
    }

    public void setHandicap(boolean handicap) {
        isHandicap = handicap;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
