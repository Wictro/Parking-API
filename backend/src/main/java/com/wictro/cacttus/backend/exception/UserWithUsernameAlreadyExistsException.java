package com.wictro.cacttus.backend.exception;

public class UserWithUsernameAlreadyExistsException extends Exception {
    public UserWithUsernameAlreadyExistsException(){
        super("User with the provided username already exists!");
    }
}
