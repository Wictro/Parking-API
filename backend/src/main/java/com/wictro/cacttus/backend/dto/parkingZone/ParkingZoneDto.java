package com.wictro.cacttus.backend.dto.parkingZone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingZoneDto {
    private Long id;
    private String name;
    private Long cityId;
    private String locationLatitude;
    private String locationLongitude;
    private Boolean isOperating;

    public ParkingZoneDto(Long id, String name, Long cityId, String locationLatitude, String locationLongitude, Boolean isOperating) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.isOperating = isOperating;
    }

    public ParkingZoneDto(){}

    @JsonProperty("isOperating")
    public Boolean getOperating() {
        return isOperating;
    }

    public void setOperating(Boolean operating) {
        isOperating = operating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
