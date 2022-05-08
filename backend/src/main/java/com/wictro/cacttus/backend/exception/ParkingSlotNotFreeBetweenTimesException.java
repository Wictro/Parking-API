package com.wictro.cacttus.backend.exception;

import java.time.LocalDateTime;

public class ParkingSlotNotFreeBetweenTimesException extends Exception {
    public ParkingSlotNotFreeBetweenTimesException(Long id, LocalDateTime timeFrom, LocalDateTime timeTo){
        super("The parking slot with id " + id + " is not free in the interval between (" + timeFrom + ")-(" + timeTo + ").");
    }
}
