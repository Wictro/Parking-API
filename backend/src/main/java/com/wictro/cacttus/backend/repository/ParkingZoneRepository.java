package com.wictro.cacttus.backend.repository;

import com.wictro.cacttus.backend.dto.parkingZone.ParkingZoneDto;
import com.wictro.cacttus.backend.model.ParkingZone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingZoneRepository extends CrudRepository<ParkingZone, Long> {
    @Query("SELECT new com.wictro.cacttus.backend.dto.parkingZone.ParkingZoneDto(p.id, p.name,  p.city.id, p.locationLatitude, p.locationLongitude, p.isOperating) from ParkingZone as p")
    List<ParkingZoneDto> getAllParkingZonesAsDto();

    @Query("SELECT new com.wictro.cacttus.backend.dto.parkingZone.ParkingZoneDto(p.id, p.name,  p.city.id, p.locationLatitude, p.locationLongitude, p.isOperating) from ParkingZone as p WHERE p.id = ?1")
    ParkingZoneDto getParkingZoneDtoBy(Long id);
}
