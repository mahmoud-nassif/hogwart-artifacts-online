package com.lak.hogwartartifactsonline.Artifact.dto;

import com.lak.hogwartartifactsonline.Wizard.dto.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto (Integer id ,
                           @NotEmpty(message = "Name is required") String name,
                           @NotEmpty(message = "Description is required") String description,
                           @NotEmpty(message = "Url is required") String imageUrl,
                           WizardDto wizard){
}
