package com.wictro.cacttus.backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_from")
    private LocalDateTime fromTime;

    @Column(name = "time_to")
    private LocalDateTime toTime;

    @Column(name = "time_created")
    private LocalDateTime createdTime;

    @Column(name = "price")
    private Double price;

    @JoinColumn(referencedColumnName = "id", name = "parking_slot")
    @ManyToOne()
    private ParkingSlot parkingSlot;

    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @ManyToOne()
    private User user;

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
