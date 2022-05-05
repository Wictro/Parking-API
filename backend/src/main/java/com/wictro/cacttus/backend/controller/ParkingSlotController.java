package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.dto.http.ErrorResponse;
import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import com.wictro.cacttus.backend.dto.parkingSlot.CreateParkingSlotsRequestDto;
import com.wictro.cacttus.backend.dto.parkingSlot.EditParkingSlotRequestDto;
import com.wictro.cacttus.backend.dto.parkingSlot.ParkingSlotDto;
import com.wictro.cacttus.backend.exception.ParkingSlotWithIdDoesNotExistException;
import com.wictro.cacttus.backend.exception.ParkingZoneWithIdDoesNotExistException;
import com.wictro.cacttus.backend.service.ParkingSlotService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/slots")
public class ParkingSlotController {
    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping
    public GenericJsonResponse<?> getAllParkingSlots(){
        return null;
    }

    //creates a specific number of parking slots belonging to some zone
    @PostMapping
    public GenericJsonResponse<?> createParkingSlots(@RequestBody CreateParkingSlotsRequestDto slots){
        try{
            parkingSlotService.saveSlots(slots);
            return new GenericJsonResponse<>(true, null);
        }
        catch (ParkingZoneWithIdDoesNotExistException e){
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    //get parking slot at specific Id
    @GetMapping("/{id}")
    public GenericJsonResponse<?> getParkingSlotWithId(@PathVariable Long id, HttpServletResponse response){
        try{
            return new GenericJsonResponse<ParkingSlotDto>(true, parkingSlotService.getSlotWithId(id));
        }
        catch (ParkingSlotWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public GenericJsonResponse<?> editParkingSlotWithId(@PathVariable Long id, HttpServletResponse response, @RequestBody EditParkingSlotRequestDto dto){
        try{
            return new GenericJsonResponse<ParkingSlotDto>(true, parkingSlotService.editSlotWithId(id, dto));
        }
        catch (ParkingSlotWithIdDoesNotExistException | ParkingZoneWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }

    //soft delete parking slot with id
    @DeleteMapping("/{id}")
    public GenericJsonResponse<?> deleteParkingSlotWithId(@PathVariable Long id, HttpServletResponse response){
        try{
            parkingSlotService.softDeleteSlotWithId(id);
            return new GenericJsonResponse<ParkingSlotDto>(true, null);
        }
        catch (ParkingSlotWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }
    }
}
