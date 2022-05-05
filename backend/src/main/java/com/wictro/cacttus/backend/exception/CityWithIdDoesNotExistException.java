package com.wictro.cacttus.backend.exception;

public class CityWithIdDoesNotExistException extends Exception{
    public CityWithIdDoesNotExistException(Long id){
        super("City with id " + id + " doesn't exist");
    }
}
