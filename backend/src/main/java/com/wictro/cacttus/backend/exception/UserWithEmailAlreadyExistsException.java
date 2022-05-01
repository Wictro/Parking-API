package com.wictro.cacttus.backend.exception;

public class UserWithEmailAlreadyExistsException extends Exception{
    public UserWithEmailAlreadyExistsException(){
        super("User with the provided email already exists!");
    }
}
