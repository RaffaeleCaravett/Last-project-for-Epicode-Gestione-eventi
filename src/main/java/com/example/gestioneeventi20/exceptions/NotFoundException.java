package com.example.gestioneeventi20.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(long id){

        super("Record con id : " + id + " non trovato.");
    }
}
