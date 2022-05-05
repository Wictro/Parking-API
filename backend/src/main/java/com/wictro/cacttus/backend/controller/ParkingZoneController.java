package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.dto.http.ErrorResponse;
import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import com.wictro.cacttus.backend.dto.parkingZone.ParkingZoneDto;
import com.wictro.cacttus.backend.exception.CityWithIdDoesNotExistException;
import com.wictro.cacttus.backend.exception.ParkingZoneWithIdDoesNotExistException;
import com.wictro.cacttus.backend.service.ParkingSlotService;
import com.wictro.cacttus.backend.service.ParkingZoneService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/zone")
public class ParkingZoneController {
    private final ParkingZoneService parkingZoneService;
    private final ParkingSlotService parkingSlotService;

    public ParkingZoneController(ParkingZoneService parkingZoneService, ParkingSlotService parkingSlotService) {
        this.parkingZoneService = parkingZoneService;
        this.parkingSlotService = parkingSlotService;
    }

    //create new Parking Zone
    @PostMapping
    public GenericJsonResponse<?> createParkingZone(@RequestBody ParkingZoneDto data, HttpServletResponse response) {
        try {
            return new GenericJsonResponse<>(true, parkingZoneService.saveParkingZone(data));
        } catch (CityWithIdDoesNotExistException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<ErrorResponse>(false, new ErrorResponse(e.getMessage()));
        }
    }

    //get all parking zones
    @GetMapping
    public GenericJsonResponse<?> getAllParkingZones() {
        return new GenericJsonResponse<>(true, parkingZoneService.getAllParkingZones());
    }

    //get parking zone with specific id
    @GetMapping("/{id}")
    public GenericJsonResponse<?> getParkingZone(@PathVariable Long id, HttpServletResponse response) {
        try {
            return new GenericJsonResponse<>(true, parkingZoneService.getParkingZoneWithId(id));
        }
        catch (ParkingZoneWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getLocalizedMessage()));
        }
    }

    //edit parking zone with id
    @PutMapping("/{id}")
    public GenericJsonResponse<?> editParkingZone(@PathVariable Long id, @RequestBody ParkingZoneDto newZone, HttpServletResponse response) {
        try {
            return new GenericJsonResponse<>(true, parkingZoneService.editParkingZone(id, newZone));
        }
        catch (ParkingZoneWithIdDoesNotExistException | CityWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getLocalizedMessage()));
        }
    }

    //delete parking zone with id (not setting the is_active but deleting it completely - reason is that a parking zone may become inactive due to construction etc)
    @DeleteMapping("/{id}")
    public GenericJsonResponse<?> softDeleteParkingZone(@PathVariable Long id, HttpServletResponse response) {
        try {
            parkingZoneService.deleteParkingZoneWithId(id);
            return new GenericJsonResponse<>(true, null);
        }
        catch (ParkingZoneWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getLocalizedMessage()));
        }
    }

    //get only the slots at a zone
    //get parking zone with specific id
    @GetMapping("/{id}/slots")
    public GenericJsonResponse<?> getParkingZoneSlots(@PathVariable Long id, HttpServletResponse response) {
        try {
            return new GenericJsonResponse<>(true, parkingSlotService.getSlotsInZone(id));
        }
        catch (ParkingZoneWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getLocalizedMessage()));
        }
    }
}