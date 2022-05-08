package com.wictro.cacttus.backend.service;

import com.wictro.cacttus.backend.dto.reservation.CreateReservationRequestDto;
import com.wictro.cacttus.backend.dto.reservation.EditReservationRequestDto;
import com.wictro.cacttus.backend.dto.reservation.ReservationDto;
import com.wictro.cacttus.backend.exception.*;
import com.wictro.cacttus.backend.model.ParkingSlot;
import com.wictro.cacttus.backend.model.Reservation;
import com.wictro.cacttus.backend.model.User;
import com.wictro.cacttus.backend.repository.ParkingSlotRepository;
import com.wictro.cacttus.backend.repository.ReservationRepository;
import com.wictro.cacttus.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ParkingSlotRepository parkingSlotRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public boolean parkingSlotIsFree(Long parkingSlotId, LocalDateTime fromTime, LocalDateTime toTime){
        return reservationRepository.getTakenReservations(parkingSlotId, fromTime, toTime).isEmpty();
    }

    public boolean parkingSlotIsFree(Long parkingSlotId, LocalDateTime fromTime, LocalDateTime toTime, Long reservationId){
        List<Reservation> reservations = reservationRepository.getTakenReservations(parkingSlotId, fromTime, toTime);
        if(reservations.size() == 1 && reservations.get(0).getId() == reservationId){
            return true;
        }
        return false;
    }

    public ReservationDto createNewReservation(CreateReservationRequestDto dto, String username) throws ParkingSlotWithIdDoesNotExistException, DateTimeException, ParkingSlotNotFreeBetweenTimesException {
        Reservation reservation = new Reservation();
        reservation.setCreatedTime(LocalDateTime.now());
        reservation.setUser(userRepository.getUserByUsername(username));
        Optional<ParkingSlot> slot = parkingSlotRepository.findById(dto.getParkingSlotId());

        if(slot.isPresent())
            reservation.setParkingSlot(slot.get());
        else{
            throw new ParkingSlotWithIdDoesNotExistException(dto.getParkingSlotId());
        }

        if(dto.getFromTime().isBefore(dto.getToTime())){
            reservation.setFromTime(dto.getFromTime());
            reservation.setToTime(dto.getToTime());
        }
        else{
            throw new DateTimeException();
        }

        if(!parkingSlotIsFree(slot.get().getId(), dto.getFromTime(), dto.getToTime())){
            throw new ParkingSlotNotFreeBetweenTimesException(slot.get().getId(), dto.getFromTime(), dto.getToTime());
        }

        //price is set with a fixed algorithm but this can change in the future
        //1.5 euros an hour
        double price = (double)ChronoUnit.HOURS.between(dto.getFromTime(), dto.getToTime()) * 1.5;
        reservation.setPrice(price);

        //persist
        reservationRepository.save(reservation);

        ReservationDto responseDto = new ReservationDto();
        responseDto.setId(reservation.getId());
        responseDto.setCreatedTime(reservation.getCreatedTime());
        responseDto.setFromTime(reservation.getFromTime());
        responseDto.setToTime(reservation.getToTime());
        responseDto.setUserId(reservation.getUser().getId());
        responseDto.setParkingSlotId(reservation.getParkingSlot().getId());
        return responseDto;
    }

    public ReservationDto getReservationDtoWithId(Long id, String username) throws ReservationWithIdDoesNotExistException, UnauthorizedExeption {
        Optional<ReservationDto> reservation = reservationRepository.getReservationDtoById(id);

        if(reservation.isEmpty())
            throw new ReservationWithIdDoesNotExistException(id);

        if(!userRepository.findById(reservation.get().getUserId()).get().getUsername().equals(username))
            throw new UnauthorizedExeption();

        return reservation.get();
    }

    public ReservationDto editReservationWithId(Long id, EditReservationRequestDto dto, String username) throws ReservationWithIdDoesNotExistException, DateTimeException, ParkingSlotNotFreeBetweenTimesException, UnauthorizedExeption {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if(reservation.isEmpty())
            throw new ReservationWithIdDoesNotExistException(id);

        if(!reservation.get().getUser().getUsername().equals(username))
            throw new UnauthorizedExeption();

        if(dto.getFromTime().isBefore(LocalDateTime.now()))
            throw new DateTimeException();

        if(dto.getFromTime().isAfter(dto.getToTime()))
            throw new DateTimeException();

        if(!parkingSlotIsFree(reservation.get().getParkingSlot().getId(), dto.getFromTime(), dto.getToTime(), reservation.get().getId()))
            throw new ParkingSlotNotFreeBetweenTimesException(reservation.get().getParkingSlot().getId(), dto.getFromTime(), dto.getToTime());

        reservation.get().setFromTime(dto.getFromTime());
        reservation.get().setToTime(dto.getToTime());

        reservationRepository.save(reservation.get());

        Optional<ReservationDto> response = reservationRepository.getReservationDtoById(id);

        return response.get();
    }

    public void deleteReservationWithId(Long id, String username) throws ReservationWithIdDoesNotExistException, UnauthorizedExeption {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if(reservation.isEmpty())
            throw new ReservationWithIdDoesNotExistException(id);

        if(!reservation.get().getUser().getUsername().equals(username))
            throw new UnauthorizedExeption();

        reservationRepository.delete(reservation.get());
    }

    public List<ReservationDto> getReservations(String name, Long userId, LocalDateTime fromTime, LocalDateTime toTime, Long slotId, Long zoneId, Long cityId) throws UnauthorizedExeption, DateTimeException {
        User requestingUser = userRepository.getUserByUsername(name);

        //if a user wants another users reservations, we don't allow it
        if(requestingUser.getRole().equals("USER") && (userId != null || userId != requestingUser.getId()))
            throw new UnauthorizedExeption();

        if(requestingUser.getRole().equals("USER"))
            userId = requestingUser.getId();

        if(fromTime.isAfter(toTime))
            throw new DateTimeException();

        if(userId != null){
            if(slotId != null)
                return reservationRepository.getAllBySlotIdAndUser(slotId, userId, fromTime, toTime);
            else if(zoneId != null)
                return reservationRepository.getAllByZoneIdAndUser(zoneId, userId, fromTime, toTime);
            else if(cityId != null)
                return reservationRepository.getAllByCityIdAndUser(cityId, userId, fromTime, toTime);
            else
                return reservationRepository.getAllByUser(userId, fromTime, toTime);
        }
        else{
            if(slotId != null)
                return reservationRepository.getAllBySlotId(slotId, fromTime, toTime);
            else if(zoneId != null)
                return reservationRepository.getAllByZoneId(zoneId, fromTime, toTime);
            else if(cityId != null)
                return reservationRepository.getAllByCityId(cityId, fromTime, toTime);
            else
                return reservationRepository.getAll(fromTime, toTime);
        }
    }
}
