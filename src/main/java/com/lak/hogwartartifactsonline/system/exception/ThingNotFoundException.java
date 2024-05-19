package com.lak.hogwartartifactsonline.system.exception;

public class ThingNotFoundException extends RuntimeException{

    public ThingNotFoundException(String thingName, int id) {
        super("could not find "+ thingName +" with id "+id);
    }
}
