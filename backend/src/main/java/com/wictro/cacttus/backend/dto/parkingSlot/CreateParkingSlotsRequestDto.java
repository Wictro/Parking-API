package com.wictro.cacttus.backend.dto.parkingSlot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateParkingSlotsRequestDto {
    private Long parkingZoneId;
    private Integer numberOfSlots;
    private Boolean isHandicap;

    public Long getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(Long parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public Integer getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(Integer numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    @JsonProperty("isHandicap")
    public Boolean isHandicap() {
        return isHandicap;
    }

    public void setHandicap(Boolean handicap) {
        isHandicap = handicap;
    }
}
