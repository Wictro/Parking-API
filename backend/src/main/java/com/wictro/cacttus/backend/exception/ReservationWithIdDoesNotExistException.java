package com.wictro.cacttus.backend.exception;

public class ReservationWithIdDoesNotExistException extends Exception{
    public ReservationWithIdDoesNotExistException(Long id){
        super("Reservation with id " + id + " does not exist!");
    }
}
