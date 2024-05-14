package com.lak.hogwartartifactsonline.Wizard.converter;

import com.lak.hogwartartifactsonline.Wizard.Wizard;
import com.lak.hogwartartifactsonline.Wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizradToWizardDtoConverter implements Converter<Wizard, WizardDto> {
    @Override
    public WizardDto convert(Wizard source) {
        return new WizardDto(source.getId(), source.getName(), source.getNumberOfArtifacts());
    }
}
