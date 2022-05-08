package com.wictro.cacttus.backend.repository;

import com.wictro.cacttus.backend.config.LocaleConfig;
import com.wictro.cacttus.backend.dto.reservation.ReservationDto;
import com.wictro.cacttus.backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.id = ?1")
    Optional<ReservationDto> getReservationDtoById(Long id);

    @Query("SELECT r FROM Reservation as r WHERE r.parkingSlot.id = ?1 AND ((r.fromTime >= ?2 AND r.fromTime < ?3) OR (r.toTime > ?2 AND r.toTime <= ?3) OR (r.fromTime <= ?2 AND r.toTime > ?3))")
    List<Reservation> getTakenReservations(Long parkingSlotId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.id = ?1 AND r.fromTime >= ?2 AND r.toTime <= ?3")
    List<ReservationDto> getAllBySlotId(Long slotId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.id = ?1 AND r.user.id = ?2 AND r.fromTime >= ?3 AND r.toTime <= ?4")
    List<ReservationDto> getAllBySlotIdAndUser(Long slotId, Long userId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.parkingZone.id = ?1 AND r.fromTime >= ?2 AND r.toTime <= ?3")
    List<ReservationDto> getAllByZoneId(Long zoneId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.parkingZone.id = ?1 AND r.user.id=?2 AND r.fromTime >= ?3 AND r.toTime <= ?4")
    List<ReservationDto> getAllByZoneIdAndUser(Long zoneId, Long userId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.parkingZone.city.id=?1 AND r.fromTime >= ?2 AND r.toTime <= ?3")
    List<ReservationDto> getAllByCityId(Long cityId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.parkingSlot.parkingZone.city.id=?1 and r.user.id=?2 AND r.fromTime >= ?3 AND r.toTime <= ?4")
    List<ReservationDto> getAllByCityIdAndUser(Long cityId, Long userId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.user.id=?1 AND r.fromTime >= ?2 AND r.toTime <= ?3")
    List<ReservationDto> getAllByUser(Long userId, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT new com.wictro.cacttus.backend.dto.reservation.ReservationDto(r.id, r.fromTime, r.toTime, r.createdTime, r.parkingSlot.id, r.user.id) FROM Reservation AS r WHERE r.fromTime >= ?1 AND r.toTime <= ?2")
    List<ReservationDto> getAll(LocalDateTime fromTime, LocalDateTime toTime);
}
