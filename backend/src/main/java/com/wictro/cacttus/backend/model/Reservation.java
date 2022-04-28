package com.wictro.cacttus.backend.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "time_from")
    private Timestamp fromTime;

    @Column(name = "time_to")
    private Timestamp toTime;

    @Column(name = "time_created")
    private Timestamp createdTime;

    @Column(name = "price")
    private double price;

    @JoinColumn(referencedColumnName = "id", name = "parking_slot")
    @ManyToOne(cascade = CascadeType.ALL)
    private ParkingSlot parkingSlot;

    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
