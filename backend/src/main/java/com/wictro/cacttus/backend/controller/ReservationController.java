package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.dto.http.ErrorResponse;
import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import com.wictro.cacttus.backend.dto.reservation.CreateReservationRequestDto;
import com.wictro.cacttus.backend.dto.reservation.EditReservationRequestDto;
import com.wictro.cacttus.backend.exception.*;
import com.wictro.cacttus.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public GenericJsonResponse<?> createNewReservation(@RequestBody CreateReservationRequestDto dto, Principal principal, HttpServletResponse response){
        try{
            return new GenericJsonResponse<>(true, reservationService.createNewReservation(dto, principal.getName()));
        }
        catch (ParkingSlotWithIdDoesNotExistException | DateTimeException | ParkingSlotNotFreeBetweenTimesException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Filter the reservations. Users can only filter their own. Admin can filter even based on user id.
     * In order: slotId, ZoneId, CityId, no location filter
     */
    @GetMapping
    public GenericJsonResponse<?> getAllReservations(Principal principal, HttpServletResponse response,
                                                     @RequestParam(required = false) Long cityId, @RequestParam(required = false) Long zoneId,
                                                     @Parameter(schema = @Schema(pattern = "^(\\d{2})-(\\d{2})-(\\d{4}) (\\d{2}):(\\d{2})$")) @RequestParam(required = true) @DateTimeFormat(pattern="dd-MM-yyyy HH:mm") LocalDateTime fromTime,
                                                     @Parameter(schema = @Schema(pattern = "^(\\d{2})-(\\d{2})-(\\d{4}) (\\d{2}):(\\d{2})$")) @RequestParam(required = true) @DateTimeFormat(pattern="dd-MM-yyyy HH:mm") LocalDateTime toTime,
                                                     @RequestParam(required = false) Long userId, @RequestParam(required = false) Long slotId){
        try{
            return new GenericJsonResponse<>(true, reservationService.getReservations(principal.getName(), userId, fromTime, toTime, slotId, zoneId, cityId));
        }
        catch (DateTimeException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
        catch (UnauthorizedExeption e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public GenericJsonResponse<?> getReservationWithId(@PathVariable Long id, Principal principal, HttpServletResponse response){
        try{
            return new GenericJsonResponse<>(true, reservationService.getReservationDtoWithId(id, principal.getName()));
        }
        catch (ReservationWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
        catch (UnauthorizedExeption e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Alter a reservation if that spot is free
     */
    @PutMapping("/{id}")
    public GenericJsonResponse<?> editReservationWithId(@PathVariable Long id, @RequestBody EditReservationRequestDto dto, Principal principal, HttpServletResponse response){
        try{
            return new GenericJsonResponse<>(true, reservationService.editReservationWithId(id, dto, principal.getName()));
        }
        catch (ReservationWithIdDoesNotExistException | DateTimeException | ParkingSlotNotFreeBetweenTimesException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
        catch (UnauthorizedExeption e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public GenericJsonResponse<?> deleteReservationWithId(@PathVariable Long id, Principal principal, HttpServletResponse response){
        try{
            reservationService.deleteReservationWithId(id, principal.getName());
            return new GenericJsonResponse<>(true, null);
        }
        catch (ReservationWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
        catch (UnauthorizedExeption e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}/invoice")
    public GenericJsonResponse<?> getReservationInvoice(@PathVariable Long id, Principal principal, HttpServletResponse response){
        try{
            return new GenericJsonResponse<>(true, reservationService.getInvoice(id, principal.getName()));
        }
        catch (ReservationWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
        catch (UnauthorizedExeption e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }



}
