package com.wictro.cacttus.backend.exception;

public class ParkingSlotWithIdDoesNotExistException extends Exception {
    public ParkingSlotWithIdDoesNotExistException(long id){
        super("Parking slot with id " + id + " does not exist!");
    }
}
