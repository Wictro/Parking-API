package com.wictro.cacttus.backend.dto.parkingSlot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingSlotDto {
    private Long id;
    private Long parkingZoneId;
    private Boolean isHandicap;
    private Boolean isFree;

    public ParkingSlotDto(Long id, Long parkingZoneId, Boolean isHandicap, Boolean isFree) {
        this.id = id;
        this.parkingZoneId = parkingZoneId;
        this.isHandicap = isHandicap;
        this.isFree = isFree;
    }

    public ParkingSlotDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(Long parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
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
