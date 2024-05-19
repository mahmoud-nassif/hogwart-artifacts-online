package com.lak.hogwartartifactsonline.Wizard.converter;

import com.lak.hogwartartifactsonline.Wizard.Wizard;
import com.lak.hogwartartifactsonline.Wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {
    @Override
    public Wizard convert(WizardDto source) {
        Wizard wizard= new Wizard();
        wizard.setId(source.id());
        wizard.setName(source.Name());
        return wizard;
    }
}
