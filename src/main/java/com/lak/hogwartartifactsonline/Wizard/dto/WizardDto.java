package com.lak.hogwartartifactsonline.Wizard.dto;

import jakarta.validation.constraints.NotEmpty;

public record WizardDto(Integer id,
                        @NotEmpty(message = "name is required") String Name,
                        Integer numberOfArtifacts) {
}
