package com.wictro.cacttus.backend.exception;

public class UnauthorizedExeption extends Exception{
    public UnauthorizedExeption(){
        super("You are not authorized to see this data!");
    }
}
