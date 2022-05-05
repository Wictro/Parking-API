package com.wictro.cacttus.backend.dto.parkingSlot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditParkingSlotRequestDto {
    private Long parkingZoneId;
    private Boolean isHandicap;
    private Boolean isFree;

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
