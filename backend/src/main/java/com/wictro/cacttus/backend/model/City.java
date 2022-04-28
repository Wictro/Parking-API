package com.wictro.cacttus.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cities")
public class City {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<ParkingZone> parkingZones;

    public List<ParkingZone> getParkingZones() {
        return parkingZones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
