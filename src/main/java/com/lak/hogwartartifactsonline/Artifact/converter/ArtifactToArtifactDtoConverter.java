package com.lak.hogwartartifactsonline.Artifact.converter;

import com.lak.hogwartartifactsonline.Artifact.Artifact;
import com.lak.hogwartartifactsonline.Artifact.dto.ArtifactDto;
import com.lak.hogwartartifactsonline.Wizard.converter.WizradToWizardDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {
    private final WizradToWizardDtoConverter wizradToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizradToWizardDtoConverter wizradToWizardDtoConverter) {
        this.wizradToWizardDtoConverter = wizradToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        return new ArtifactDto(source.getId(), source.getName(), source.getDescription(), source.getImageUrl(),
                source.getWizard()!=null ? this.wizradToWizardDtoConverter.convert(source.getWizard()):null);
    }
}
