package com.wictro.cacttus.backend.exception;

public class ParkingZoneWithIdDoesNotExistException extends Exception {
    public ParkingZoneWithIdDoesNotExistException(Long id){
        super("Parking Zone With id " + id + " does not exist!");
    }
}
