package com.wictro.cacttus.backend.repository;

import com.wictro.cacttus.backend.dto.parkingSlot.ParkingSlotDto;
import com.wictro.cacttus.backend.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    @Query("SELECT new com.wictro.cacttus.backend.dto.parkingSlot.ParkingSlotDto(p.id, p.parkingZone.id, p.isHandicap, p.isFree) FROM ParkingSlot as p where p.parkingZone.id = ?1")
    List<ParkingSlotDto> getAllSlotsAtZone(Long zoneId);
}
