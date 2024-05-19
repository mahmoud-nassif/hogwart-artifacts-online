package com.lak.hogwartartifactsonline.Wizard;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(String message) {
        super("could not find wizard with id "+message);
    }
}
